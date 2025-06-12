package com.axa.simple_auth_axa_req.exception;


import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(String message, Object data) {
        super(HttpStatus.UNAUTHORIZED, message, data);
    }
}
