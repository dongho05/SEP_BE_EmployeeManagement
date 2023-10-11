package com.project.SEP_BE_EmployeeManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PositionDto {
    private Long id;
    private String name;
    private Date createdDate;
    private Date updatedDate;
}
