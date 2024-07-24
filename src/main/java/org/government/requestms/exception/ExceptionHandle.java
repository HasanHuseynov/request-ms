package org.government.requestms.exception;

import feign.FeignException;
import org.government.requestms.dto.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<String> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return BaseResponse.fail(errorMessage);
    }


    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse<String> handleDataNotFoundException(DataNotFoundException e) {
        return BaseResponse.fail(e.getMessage());
    }

    @ExceptionHandler(DataExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseResponse<String> handleDataExistException(DataExistException e) {
        return BaseResponse.fail(e.getMessage());

    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<?> handleFeignNotFoundException(FeignException.NotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.contentUTF8());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse<String> handleException(Exception e) {
        return BaseResponse.fail("Server error : " + e.getMessage());
    }
}
