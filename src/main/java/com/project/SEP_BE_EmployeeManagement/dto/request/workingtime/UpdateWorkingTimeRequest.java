package com.project.SEP_BE_EmployeeManagement.dto.request.workingtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWorkingTimeRequest {
    private LocalTime startTime;
    private LocalTime endTime;
}
