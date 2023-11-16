package com.project.SEP_BE_EmployeeManagement.dto.response;

import com.project.SEP_BE_EmployeeManagement.dto.ContractDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponse {
    List<ContractDto> content;
    int number;
    int size;
    int totalElements;
    int totalPages;

}
