package com.platform.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.platform.common.dto.ApiResponse;
import com.platform.common.exception.PlatformException;
import com.platform.common.exception.ResourceNotFoundException;

/**
 * Global exception handler for the platform.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Logger instance. */
    private static final Logger LOG = LoggerFactory.getLogger(
            GlobalExceptionHandler.class);

    /**
     * Handles ResourceNotFoundException.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ApiResponse<Void>> handleNotFoundException(
            final ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage()));
    }

    /**
     * Handles PlatformException.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(PlatformException.class)
    public final ResponseEntity<ApiResponse<Void>> handlePlatformException(
            final PlatformException ex) {
        LOG.error("Platform error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ex.getMessage()));
    }

    /**
     * Handles general exceptions.
     *
     * @param ex the exception
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiResponse<Void>> handleGeneralException(
            final Exception ex) {
        LOG.error("Unexpected error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("An unexpected error occurred: "
                        + ex.getMessage()));
    }
}
