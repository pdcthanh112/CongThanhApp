package com.congthanh.orderservice.exception.handler;

import com.congthanh.orderservice.constant.common.ResponseStatus;
import com.congthanh.orderservice.exception.BadRequestException;
import com.congthanh.orderservice.model.response.Response;
import com.congthanh.orderservice.exception.NotFoundException;
import com.congthanh.orderservice.exception.PermissionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler extends RuntimeException {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException exception) {
        Response response = new Response();
        response.setMessage(exception.getMessage());
        response.setStatus(ResponseStatus.STATUS_FAILED);
        return ResponseEntity.badRequest().body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = PermissionException.class)
    public ResponseEntity<Object> permissionException(PermissionException exception) {
        Response response = new Response();
        response.setMessage(exception.getMessage());
        response.setStatus(ResponseStatus.STATUS_FAILED);
        return ResponseEntity.badRequest().body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> badRequestException(BadRequestException exception) {
        Response response = new Response();
        response.setMessage(exception.getMessage());
        response.setStatus(ResponseStatus.STATUS_FAILED);
        return ResponseEntity.badRequest().body(response);
    }
}
