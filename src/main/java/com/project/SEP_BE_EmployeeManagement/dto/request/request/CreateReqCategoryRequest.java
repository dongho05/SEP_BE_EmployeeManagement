package com.project.SEP_BE_EmployeeManagement.dto.request.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequestCategoryReq {
    private String requestCategoryName;
}
