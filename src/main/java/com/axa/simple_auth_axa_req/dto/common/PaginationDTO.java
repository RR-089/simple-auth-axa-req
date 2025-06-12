package com.axa.simple_auth_axa_req.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationDTO<T> {
    private final long totalRecords;
    private final long totalPages;
    private T data;
}
