package com.project.SEP_BE_EmployeeManagement.dto.request.request;

import com.project.SEP_BE_EmployeeManagement.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequestReq extends BaseEntity {
    private String requestTitle;

    private String requestContent;

//    private LocalTime startTime;
//
//    private LocalTime endTime;

    private LocalDate startDate;

    private LocalDate endDate;

    private long acceptBy;

    private LocalDate acceptAt;

    private boolean status;

}
