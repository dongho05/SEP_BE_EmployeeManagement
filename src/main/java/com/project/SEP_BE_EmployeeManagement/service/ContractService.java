package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.model.Contract;
import org.springframework.stereotype.Service;

@Service
public interface ContractService {
    public Contract getCurrentContractByUserId(long userId);
}
