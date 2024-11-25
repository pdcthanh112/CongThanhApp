package com.congthanh.inventoryservice.exception.handler;

import com.congthanh.inventoryservice.constant.common.ResponseStatus;
import com.congthanh.inventoryservice.exception.ecommerce.BadRequestException;
import com.congthanh.inventoryservice.exception.ecommerce.NotFoundException;
import com.congthanh.inventoryservice.exception.ecommerce.PermissionException;
import com.congthanh.inventoryservice.model.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers extends RuntimeException {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException exception) {
        Response response = new Response();
        response.setMessage(exception.getMessage());
        response.setStatus(ResponseStatus.STATUS_FAILED);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = PermissionException.class)
    public ResponseEntity<Object> permissionException(PermissionException exception) {
        Response response = new Response();
        response.setMessage(exception.getMessage());
        response.setStatus(ResponseStatus.STATUS_FAILED);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> badRequestException(BadRequestException exception) {
        Response response = new Response();
        response.setMessage(exception.getMessage());
        response.setStatus(ResponseStatus.STATUS_FAILED);
        return ResponseEntity.badRequest().body(response);
    }
}
