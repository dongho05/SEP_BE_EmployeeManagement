package com.project.SEP_BE_EmployeeManagement.dto.response.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRes {
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
}
