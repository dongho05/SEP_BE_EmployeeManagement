package com.project.SEP_BE_EmployeeManagement.dto.request.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReqTypeRequest {
    private String requestTypeName;
    private int requestCategoryId;
    private String replacementPerson;
    private String replacementWork;
}
