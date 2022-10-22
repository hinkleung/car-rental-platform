package com.prudential.assignment.common.exception;

import com.prudential.assignment.common.model.response.Response;
import com.prudential.assignment.common.model.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public Response handleBusinessException(BusinessException e) {
        log.error("BusinessException: ", e);
        if (e.getErrorCode() != null) {
            return Response.error(e.getErrorCode(), e.getMessage());
        }
        return Response.error(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response handleValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: ", e);
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return Response.invalidParam(message);
    }

    @ExceptionHandler(value = BindException.class)
    public Response handleValidException(BindException e) {
        log.error("BindException: ", e);
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return Response.invalidParam(message);
    }

    @ExceptionHandler(value = Exception.class)
    public Response handleException(Exception e) {
        log.error("Exception: ", e);
        return Response.error(ResponseCode.UNKNOWN_ERROR);
    }


}
