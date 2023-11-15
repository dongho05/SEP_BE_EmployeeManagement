package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.ContractDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.contract.CreateContractRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.contract.UpdateContractRequest;
import com.project.SEP_BE_EmployeeManagement.model.Contract;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContractService {
    public Contract getCurrentContractByUserId(long userId);
    List<Contract> GetAll();
    Page<ContractDto> getData(String search, Integer pageNo, Integer pageSize);

    Contract createContract(CreateContractRequest request) throws NotFoundException;

    Contract updateContract(Long contractId, CreateContractRequest updateContractRequest) throws NotFoundException;

    void deleteContract(Long contractId) throws NotFoundException;
}
