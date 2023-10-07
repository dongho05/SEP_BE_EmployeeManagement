package com.project.SEP_BE_EmployeeManagement.dto.request.holiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayRequest {
    private String holidayName;

    private LocalDate startDate;

    private LocalDate endDate;
}
