package com.project.SEP_BE_EmployeeManagement.dto.response.request;

import com.project.SEP_BE_EmployeeManagement.model.Department;
import com.project.SEP_BE_EmployeeManagement.model.RequestType;
import com.project.SEP_BE_EmployeeManagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestResponse {
    private Long id;

    private String requestTitle;

    private String requestContent;

    private LocalTime startTime;

    private LocalTime endTime;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long userId;

    private int requestTypeId;

    private Date createdDate;

    private String createdBy;

    private Date updatedDate;

    private String updatedBy;

    private int status;

    private int departmentId;

    private Department department;

    private User user;

    private RequestType requestType;

    private long numberOfDays;
}
