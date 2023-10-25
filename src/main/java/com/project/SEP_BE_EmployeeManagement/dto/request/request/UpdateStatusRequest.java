package com.project.SEP_BE_EmployeeManagement.dto.request.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusRequest {
    private int status;
    private String note;
}
