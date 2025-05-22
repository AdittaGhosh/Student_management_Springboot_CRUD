package com.student.exception;

import com.student.controller.BaseController;
import com.student.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleDbError(DatabaseException ex) {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationErrors(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            sb.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        }
        return errorResponse(HttpStatus.BAD_REQUEST, sb.toString().trim());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFound(EntityNotFoundException ex) {
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage() != null ? ex.getMessage() : "Requested entity not found.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error: " + ex.getMessage());
    }
}