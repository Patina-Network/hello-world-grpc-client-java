package org.patinanetwork.helloworld.utilities.exception;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.patinanetwork.helloworld.dto.error.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<ErrorDto> handleGrpcFailure(
            final StatusRuntimeException ex, final HttpServletRequest request) {
        Status status = ex.getStatus();
        Status.Code code = status.getCode();
        log.warn(
                "gRPC request failed: status={} description={} request=\"{} {}\"",
                code,
                status.getDescription(),
                request.getMethod(),
                request.getRequestURI());
        return switch (code) {
            case INVALID_ARGUMENT -> error(HttpStatus.BAD_REQUEST, "invalid request");
            case NOT_FOUND -> error(HttpStatus.NOT_FOUND, "not found");
            case ALREADY_EXISTS -> error(HttpStatus.CONFLICT, "already exists");
            case UNAUTHENTICATED -> error(HttpStatus.UNAUTHORIZED, "authentication required");
            case PERMISSION_DENIED -> error(HttpStatus.FORBIDDEN, "permission denied");
            case RESOURCE_EXHAUSTED -> error(HttpStatus.TOO_MANY_REQUESTS, "resource exhausted");
            case UNAVAILABLE -> error(HttpStatus.SERVICE_UNAVAILABLE, "service unavailable");
            case DEADLINE_EXCEEDED, CANCELLED -> error(HttpStatus.GATEWAY_TIMEOUT, "upstream timeout");
            default -> error(HttpStatus.BAD_GATEWAY, "upstream request failed");
        };
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleUnexpected(final Exception ex, final HttpServletRequest request) {
        log.error("HTTP request failed: request=\"{} {}\"", request.getMethod(), request.getRequestURI(), ex);
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error");
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            final Exception ex,
            final Object body,
            final HttpHeaders headers,
            final HttpStatusCode statusCode,
            final WebRequest request) {
        var message = switch (ex) {
            case HttpMessageNotReadableException e -> "invalid JSON request";
            case HttpMediaTypeNotSupportedException e -> "application/json required";
            case NoResourceFoundException e -> "not found";
            default -> HttpStatus.valueOf(statusCode.value()).getReasonPhrase().toLowerCase();
        };
        return ResponseEntity.status(statusCode).headers(headers).body(ErrorDto.of(message));
    }

    private static ResponseEntity<ErrorDto> error(final HttpStatus status, final String message) {
        return ResponseEntity.status(status).body(ErrorDto.of(message));
    }
}
