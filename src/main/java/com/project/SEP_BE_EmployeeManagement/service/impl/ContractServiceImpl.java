package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.model.Contract;
import com.project.SEP_BE_EmployeeManagement.repository.ContractRepository;
import com.project.SEP_BE_EmployeeManagement.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractRepository contractRepository;
    @Override
    public Contract getCurrentContractByUserId(long userId) {
        List<Contract> contracts = contractRepository.getContractsByUser(userId);
        Contract contract ;
        if(contracts.size()>0){
            contract = contracts.get(0);
            return contract;
        }
        return null;
    }
}
