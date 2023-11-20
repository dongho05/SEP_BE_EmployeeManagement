package com.project.SEP_BE_EmployeeManagement.dto.response.exception;

public class UpdateNullException extends RuntimeException{
    public UpdateNullException(String message) {
        super(message);
    }
}
