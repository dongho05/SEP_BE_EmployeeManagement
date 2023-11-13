package com.project.SEP_BE_EmployeeManagement.dto;
import com.project.SEP_BE_EmployeeManagement.model.Department;
import com.project.SEP_BE_EmployeeManagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractDto {
    private Long id;
    private String nameContract;
    private String fileContract;
    private Date createdDate;
    private Date updatedDate;
    private User user;
    private Department department;

}
