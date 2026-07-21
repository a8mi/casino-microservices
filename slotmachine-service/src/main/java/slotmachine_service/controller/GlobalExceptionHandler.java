package slotmachine_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import slotmachine_service.api.ErrorResponse;
import slotmachine_service.exception.BankingServiceException;
import slotmachine_service.exception.GameNotFoundException;
import slotmachine_service.exception.InsufficientFundsException;
import slotmachine_service.exception.UserNotFoundException;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, GameNotFoundException.class})
    ResponseEntity<ErrorResponse> handleNotFound(RuntimeException exception, HttpServletRequest request) {
        return response(HttpStatus.NOT_FOUND, exception.getMessage(), request);
    }

    @ExceptionHandler({InsufficientFundsException.class, IllegalArgumentException.class})
    ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException exception, HttpServletRequest request) {
        return response(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return response(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(BankingServiceException.class)
    ResponseEntity<ErrorResponse> handleBanking(BankingServiceException exception, HttpServletRequest request) {
        return response(HttpStatus.BAD_GATEWAY, exception.getMessage(), request);
    }

    private static ResponseEntity<ErrorResponse> response(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(status).body(new ErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        ));
    }
}
