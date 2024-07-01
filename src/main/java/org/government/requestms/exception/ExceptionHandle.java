package org.government.requestms.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getBindingResult().getFieldError().getDefaultMessage());
        errorDetails.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotfoundException(RequestNotFoundException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ExistCategoryException.class)
    public ResponseEntity<ErrorDetails> handleUserNotfoundException(ExistCategoryException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(e.getMessage());
        errorDetails.setStatusCode(HttpStatus.CONFLICT.value());
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<?> handleFeignNotFoundException(FeignException.NotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.contentUTF8());
    }
}
