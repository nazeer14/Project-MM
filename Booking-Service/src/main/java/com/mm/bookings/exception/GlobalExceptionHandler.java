package com.mm.bookings.exception;

import com.mm.bookings.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 1) Order not found (custom)
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiResponse> handleOrderNotFound(OrderNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(status.value(), ex.getMessage()));
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse> handleOrderNotFound(CustomerNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(status.value(), ex.getMessage()));
    }

    // 2) Business rule violations
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse> handleIllegalState(IllegalStateException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(status.value(), ex.getMessage()));
    }

    // 3) @Valid on @RequestBody (field-level validation errors)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(status.value(), errorMessage));
    }

    // 4) @Valid on path params / request params
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleConstraintViolation(ConstraintViolationException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(status.value(), errorMessage));
    }

    // 5) Invalid / malformed JSON body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(status.value(), "Malformed JSON request"));
    }

    // 6) DB constraint violations (unique, FK, etc.)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(status.value(), "Database constraint violation"));
    }



//    // 7) Errors from other microservices via Feign
//    @ExceptionHandler(FeignException.class)
//    public ResponseEntity<ApiResponse> handleFeignException(FeignException ex) {
//        // Map Feign status to your response
//        int feignStatus = ex.status();
//        HttpStatus status = feignStatus > 0 ? HttpStatus.valueOf(feignStatus) : HttpStatus.BAD_GATEWAY;
//
//        String message = "Downstream service error: " + status.value();
//        if (ex.contentUTF8() != null && !ex.contentUTF8().isBlank()) {
//            message = message + " - " + ex.contentUTF8();
//        }
//
//        return ResponseEntity
//                .status(status)
//                .body(ApiResponse.error(status.value(), message));
//    }

    // 8) Fallback for any unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
        log.error(ex.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(status.value(), "Internal server error"));
    }
}
