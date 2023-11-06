package com.project.SEP_BE_EmployeeManagement.dto.request.attendance;

import com.project.SEP_BE_EmployeeManagement.model.ESign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSignInAttendanceRequest {
    private String reason;
    private ESign sign;

}
