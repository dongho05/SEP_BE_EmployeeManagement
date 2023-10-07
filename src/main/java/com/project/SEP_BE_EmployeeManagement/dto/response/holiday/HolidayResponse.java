package com.project.SEP_BE_EmployeeManagement.dto.response.holiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayResponse {
    private int id;
    private LocalDate startDate;
    private String holidayName;
    private int totalDayOff;
}
