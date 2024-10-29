package com.vnc.officeManagementApp.ResponseDTO;

import lombok.Getter;

@Getter
public class ErrorResponseDTO {
    private final int statusCode;
    private final Object body;
    private final boolean error;

    // Constructor with default status code and error flag for failure
    public ErrorResponseDTO(Object body) {
        this.statusCode = 500; // Default error status code
        this.body = body;
        this.error = true; // Default error flag for error
    }

    // Constructor with custom status code
    public ErrorResponseDTO(int statusCode, Object body) {
        this.statusCode = statusCode;
        this.body = body;
        this.error = true; // Error flag remains true for error
    }
}
