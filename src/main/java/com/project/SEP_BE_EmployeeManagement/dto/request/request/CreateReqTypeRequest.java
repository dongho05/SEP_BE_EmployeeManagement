package com.project.SEP_BE_EmployeeManagement.dto.request.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRequestTypeReq {
    private String requestTypeName;
    private int requestCategoryId;
    private String replacementPerson;
    private String replacementWork;
}
