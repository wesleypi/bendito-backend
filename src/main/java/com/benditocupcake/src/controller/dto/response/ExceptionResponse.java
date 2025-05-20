package com.benditocupcake.src.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionResponse {
    private String httpStatus;
    private String message;
}