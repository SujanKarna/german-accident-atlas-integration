package com.sujan.accident.analytics.exception;

import com.sujan.accident.analytics.dto.ApiResponseDto;
import com.sujan.accident.analytics.dto.ErrorResponse;
import com.sujan.accident.analytics.exception.unfall.InvalidStateCodeException;
import com.sujan.accident.analytics.exception.unfall.InvalidYearException;
import com.sujan.accident.analytics.exception.unfall.NoDataForYearException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildError(int status, String message) {
        return new ErrorResponse(status, message, Instant.now().toString());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid value for parameter '" + ex.getName() +
                "'. Expected type: " + ex.getRequiredType().getSimpleName();
        return ResponseEntity.badRequest().body(buildError(400, message));
    }

    @ExceptionHandler(InvalidStateCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidState(InvalidStateCodeException ex) {
        return ResponseEntity.badRequest().body(buildError(400, ex.getMessage()));
    }

    @ExceptionHandler(InvalidYearException.class)
    public ResponseEntity<ErrorResponse> handleInvalidYear(InvalidYearException ex) {
        return ResponseEntity.badRequest().body(buildError(400, ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(buildError(400, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(500, "Internal server error"));
    }
    @ExceptionHandler(NoDataForYearException.class)
    public ResponseEntity<ErrorResponse> handleNoDataForYear(NoDataForYearException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.OK.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.OK).body(error);
    }

    @ExceptionHandler(MetadataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponseDto<?> handleMetadataNotFound(MetadataNotFoundException ex) {
        return new ApiResponseDto<>(
                ex.getMessage(),
                null,
                null
        );
    }

}
