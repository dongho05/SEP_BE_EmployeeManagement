package com.project.SEP_BE_EmployeeManagement.dto;

import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import lombok.Data;

import java.util.List;
@Data
public class UserAttendance {
//    private List<Attendance> attendances;
//
//    private String code;
//    private String name;
public String getCode() {
    return code;
}

    public void setCode(String code) {
        this.code = code;
    }

    public UserAttendance() {
    }

    public List<Attendance> getLogDetail() {
        return logDetail;
    }

    public void setLogDetail(List<Attendance> logDetail) {
        this.logDetail = logDetail;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<Attendance> logDetail;

    private String code;
    private String name;
}
