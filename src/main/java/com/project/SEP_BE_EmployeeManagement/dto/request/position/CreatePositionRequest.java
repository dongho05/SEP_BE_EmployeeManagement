package com.project.SEP_BE_EmployeeManagement.dto.request.position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreatePositionRequest {
    private String positionName;
    private long roleId;
}
