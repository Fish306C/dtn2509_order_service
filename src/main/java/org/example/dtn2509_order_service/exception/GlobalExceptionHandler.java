package org.example.dtn2509_order_service.exception;

import org.example.dtn2509_order_service.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<BaseResponse<Object>> handleApplicationException(ApplicationException e)
    {
        return ResponseEntity.status(e.getHttpStatus()).body(new BaseResponse<>(null, e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e)
    {
        return ResponseEntity.badRequest().body(new BaseResponse<>(null, "Request body is missing or invalid!"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        var fieldError = e.getBindingResult().getFieldError();
        var message = fieldError == null ? "Invalid request!" : fieldError.getField() + " " + fieldError.getDefaultMessage();
        return ResponseEntity.badRequest().body(new BaseResponse<>(null, message));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse<Object>> handleRuntimeException(RuntimeException e)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(null, "System error, please try later!"));
    }
}
