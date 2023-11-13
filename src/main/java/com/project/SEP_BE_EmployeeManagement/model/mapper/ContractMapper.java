package com.project.SEP_BE_EmployeeManagement.model.mapper;

import com.project.SEP_BE_EmployeeManagement.dto.ContractDto;
import com.project.SEP_BE_EmployeeManagement.dto.PositionDto;
import com.project.SEP_BE_EmployeeManagement.model.Contract;
import com.project.SEP_BE_EmployeeManagement.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Collectors;

public class ContractMapper {

    public static ContractDto toDto(Contract contract) {
        ContractDto dto = new ContractDto();
        dto.setId(contract.getId());
        dto.setNameContract(contract.getContractName());
        dto.setFileContract(contract.getFileName());
        dto.setCreatedDate(contract.getCreatedDate());
        dto.setUpdatedDate(contract.getUpdatedDate());
        dto.setUser(contract.getUser());
        dto.setDepartment(contract.getUser().getDepartment());
        return dto;
    }

    public static Page<ContractDto > toDtoPage(Page<Contract> contractPage) {
        return new PageImpl<>(
                contractPage.getContent().stream()
                        .map(ContractMapper::toDto)
                        .collect(Collectors.toList()),
                PageRequest.of(contractPage.getPageable().getPageNumber(), contractPage.getPageable().getPageSize()),
                contractPage.getTotalElements()
        );
    }
}
