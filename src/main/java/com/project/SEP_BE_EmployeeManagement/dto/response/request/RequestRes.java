package com.project.SEP_BE_EmployeeManagement.dto.response.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
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

    private long acceptBy;

    private LocalDate acceptAt;

    private boolean status;
}
