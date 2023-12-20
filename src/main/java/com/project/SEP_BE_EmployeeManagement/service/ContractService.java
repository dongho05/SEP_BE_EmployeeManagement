package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.ContractDto;
import com.project.SEP_BE_EmployeeManagement.dto.DepartmentDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.contract.CreateContractRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.contract.UpdateContractRequest;
import com.project.SEP_BE_EmployeeManagement.model.Contract;
import com.project.SEP_BE_EmployeeManagement.model.User;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContractService {
    public Contract getCurrentContractByUserId(long userId);
    List<Contract> GetAll();
    Page<ContractDto> getData(String search, String deptId, int pageNo,int pageSize);

    Page<Contract> getDataTest(String search, String deptId, String empId, int pageNo,int pageSize);

    ContractDto getContractById(long id) throws NotFoundException;

    Contract createContract(CreateContractRequest request) throws NotFoundException;

    Contract updateContract(Long contractId, CreateContractRequest updateContractRequest) throws NotFoundException;

    void deleteContract(Long contractId) throws NotFoundException;

    List<User> listEmployeeContact(String id);
}
