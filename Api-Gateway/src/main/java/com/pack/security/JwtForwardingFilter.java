//package com.pack.security;
//
//import io.jsonwebtoken.Claims;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Component
//public class JwtForwardingFilter implements GlobalFilter, Ordered {
//
//    private final JwtUtil jwtUtil;
//
//    public JwtForwardingFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//
//            try {
//                if (jwtUtil.validateToken(token)) {
//                    Claims claims = jwtUtil.extractAllClaims(token);
//
//                    // ✅ Extract userId and roles safely
//                    String userId = claims.get("id", String.class);
//                    if (userId == null) {
//                        userId = claims.getSubject();
//                    }
//
//                    Object rolesObj = claims.get("roles");
//                    String roles;
//
//                    if (rolesObj instanceof List<?> roleList) {
//                        roles = roleList.stream()
//                                .map(Object::toString)
//                                .collect(Collectors.joining(","));
//                    } else {
//                        roles = String.valueOf(rolesObj);
//                    }
//
//                    // ✅ Add headers for downstream microservices
//                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
//                            .header("X-Authorization", "Bearer " + token)
//                            .header("X-User-Id", userId != null ? userId : "")
//                            .header("X-Roles", roles != null ? roles : "")
//                            .build();
//
//                    log.debug("Forwarded JWT for userId={} with roles={}", userId, roles);
//
//                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
//                }
//            } catch (Exception e) {
//                log.warn("JWT validation/forwarding failed: {}", e.getMessage());
//            }
//        }
//
//        // Continue normally if no valid token
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        // Run early in the filter chain
//        return -1;
//    }
//}
