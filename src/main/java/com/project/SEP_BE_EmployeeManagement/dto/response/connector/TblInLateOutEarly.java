package com.project.SEP_BE_EmployeeManagement.dto.response.connector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TblInLateOutEarly {
    private String EmployeeId;
    private Date IODate;
    private Integer IOKind;
    private Date IOStart;
    private Date IOEnd;
    private Integer IOMinutes;
    private Integer IOMinutesDeduct;
    private Boolean ApprovedDeduct;
    private Integer StatusID;
    private String Reason;
    private Integer Period;
}
