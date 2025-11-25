package com.pack.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class GatewaySecurityConfig {

    private final JwtUtil jwtUtil;

    // ✅ Validate JWT and extract user info
    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return authentication -> {
            String token = (String) authentication.getCredentials();

            try {
                if (!jwtUtil.validateToken(token)) {
                    return Mono.error(new RuntimeException("Invalid JWT token"));
                }

                Claims claims = jwtUtil.extractAllClaims(token);
                String userId = claims.get("id", String.class);
                if (userId == null) userId = claims.getSubject(); // fallback to 'sub'

                Object rolesObj = claims.get("roles");
                List<SimpleGrantedAuthority> authorities = null;

                if (rolesObj instanceof List<?> roleList) {
                    authorities = roleList.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList());
                }

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userId, token, authorities);
                log.info("userId : {} {}",userId,authorities);
                return Mono.just(authToken);

            } catch (ExpiredJwtException e) {
                return Mono.error(new RuntimeException("JWT token has expired"));
            }
        };
    }

    // ✅ Convert header into Authentication object
    @Bean
    public ServerAuthenticationConverter authenticationConverter() {
        return exchange -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return Mono.empty();
            }
            String token = authHeader.substring(7);
            return Mono.just(new UsernamePasswordAuthenticationToken(null, token));
        };
    }

    // ✅ Security filter chain
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ReactiveAuthenticationManager authenticationManager,
                                                         ServerAuthenticationConverter authenticationConverter) {

        AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter(authenticationManager);
        jwtFilter.setServerAuthenticationConverter(authenticationConverter);
        jwtFilter.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance());

        // ✅ Forward token + userId + roles headers to microservices
        WebFilter tokenForwardingFilter = (exchange, chain) ->
                ReactiveSecurityContextHolder.getContext()
                        .flatMap(ctx -> {
                            Authentication auth = ctx.getAuthentication();
                            if (auth == null) return chain.filter(exchange);

                            String token = (String) auth.getCredentials();
                            String userId = (String) auth.getPrincipal();
                            String roles = auth.getAuthorities().stream()
                                    .map(a -> a.getAuthority())
                                    .collect(Collectors.joining(","));

                            ServerWebExchange mutated = exchange.mutate()
                                    .request(exchange.getRequest().mutate()
                                            .header("Authorization", "Bearer " + token)
                                            .header("X-User-Id", userId != null ? userId : "")
                                            .header("X-Roles", roles != null ? roles : "")
                                            .build())
                                    .build();
                            log.debug("Forwarded JWT for userId={} with roles={}", userId, roles);
                            return chain.filter(mutated);
                        })
                        .switchIfEmpty(chain.filter(exchange));

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/customer-service/api/v1/auth/**",
                                "/actuator/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-resources/**"
                        ).permitAll()
                        .pathMatchers("/admin/**").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAfter(tokenForwardingFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((exchange, e) -> {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        })
                )
                .build();
    }
}
