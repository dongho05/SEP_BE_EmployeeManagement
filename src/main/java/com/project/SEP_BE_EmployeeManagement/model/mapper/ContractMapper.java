package com.project.SEP_BE_EmployeeManagement.model.mapper;

import com.project.SEP_BE_EmployeeManagement.dto.ContractDto;
import com.project.SEP_BE_EmployeeManagement.dto.PositionDto;
import com.project.SEP_BE_EmployeeManagement.model.Contract;
import com.project.SEP_BE_EmployeeManagement.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContractMapper {
//    public static Contract toEntity(ContractDto contractDto) {
//        Contract contract = new Contract();
//        contract.setId(contractDto.getId());
//        contract.setContractName(contractDto.getNameContract());
//        contract.setFileName(contractDto.getFileContract());
//        contract.setCreatedDate(contractDto.getCreatedDate());
//        contract.setUpdatedDate(contractDto.getUpdatedDate());
//        contract.setUser(contractDto.getUser());
//        contract.getUser().setDepartment(contractDto.getDepartment());
//        return contract;
//    }

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
        System.out.println(contractPage.getContent());
        System.out.println("test nhan");

        List<ContractDto> contractDtoList = new ArrayList<>();
        for (Object obj : contractPage.getContent()) {
            if (obj instanceof Contract) {
                Contract contract = (Contract) obj;
                contractDtoList.add(toDto(contract));
            }
        }
        System.out.println(contractDtoList);


        return new PageImpl<>(

                contractPage.getContent().stream()
                        .map(ContractMapper::toDto)
                        .collect(Collectors.toList()),
                PageRequest.of(contractPage.getPageable().getPageNumber(), contractPage.getPageable().getPageSize()),
                contractPage.getTotalElements()
        );
    }
}
