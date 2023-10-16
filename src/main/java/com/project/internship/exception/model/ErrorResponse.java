package com.project.internship.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private Class<?> type;
    private String message;
    private List<String> errors;

    public ErrorResponse(String code, Class<?> type, String message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }
}
