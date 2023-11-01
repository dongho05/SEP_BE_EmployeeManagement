package com.project.SEP_BE_EmployeeManagement.dto.response.attendance;

import com.project.SEP_BE_EmployeeManagement.model.Sign;
import com.project.SEP_BE_EmployeeManagement.model.User;
import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceResponse {
    private Long id;
    private String department;
    private String employeeCode;
    private String employeeName;
    private LocalDate dateLog;
    private LocalTime timeIn;
    private LocalTime timeOut;
}
