package com.project.SEP_BE_EmployeeManagement.dto.request.attendance;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EditAttendance {
    @NotNull
    private String code;

    @NotBlank
    private String date;

    private String reason;

    private String codeAdminEdit;

    @NotNull
    private String sign;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCodeAdminEdit() {
        return codeAdminEdit;
    }

    public void setCodeAdminEdit(String codeAdminEdit) {
        this.codeAdminEdit = codeAdminEdit;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}