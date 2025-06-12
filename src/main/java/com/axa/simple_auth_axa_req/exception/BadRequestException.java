package com.axa.simple_auth_axa_req.exception;


import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException {
    public BadRequestException(String message, Object data) {
        super(HttpStatus.BAD_REQUEST, message, data);
    }
}
