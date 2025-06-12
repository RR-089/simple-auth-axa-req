package com.axa.simple_auth_axa_req.exception;


import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException {
    public NotFoundException(String message, Object data) {
        super(HttpStatus.NOT_FOUND, message, data);
    }
}
