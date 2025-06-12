package com.axa.simple_auth_axa_req.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO<T> {
    private long status;
    private String message;
    private T data;
}
