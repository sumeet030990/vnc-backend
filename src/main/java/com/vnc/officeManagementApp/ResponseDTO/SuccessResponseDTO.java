package com.vnc.officeManagementApp.ResponseDTO;

import lombok.Getter;

@Getter
public class SuccessResponseDTO {
    private final int statusCode;
    private final Object body;
    private final boolean error;

    // Constructor with default status code and error flag for success
    public SuccessResponseDTO(Object body) {
        this.statusCode = 200; // Default success status code
        this.error = false; // Default error flag for success
        this.body = body;
    }

    // Constructor with custom status code
    public SuccessResponseDTO(int statusCode, Object body) {
        this.statusCode = statusCode;
        this.error = false; // Error flag remains false for success
        this.body = body;
    }
}
