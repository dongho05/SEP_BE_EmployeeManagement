package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.ContractDto;
import com.project.SEP_BE_EmployeeManagement.dto.PositionDto;
import com.project.SEP_BE_EmployeeManagement.dto.UserDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.CreateUser;
import com.project.SEP_BE_EmployeeManagement.dto.request.contract.CreateContractRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.contract.UpdateContractRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.position.CreatePositionRequest;
import com.project.SEP_BE_EmployeeManagement.model.Contract;
import com.project.SEP_BE_EmployeeManagement.model.Position;
import com.project.SEP_BE_EmployeeManagement.model.Role;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.model.mapper.ContractMapper;
import com.project.SEP_BE_EmployeeManagement.model.mapper.PositionMapper;
import com.project.SEP_BE_EmployeeManagement.model.mapper.UserMapper;
import com.project.SEP_BE_EmployeeManagement.repository.ContractRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.service.ContractService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    FileManagerService fileManagerService;
    @Autowired
    private UserRepository userRepository;
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

    @Override
    public List<Contract> GetAll() {
        return contractRepository.findAll();
    }

    @Override
    public Page<ContractDto> getData(String search, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Contract> page = contractRepository.getContract(search, pageable);

        Page<ContractDto> response = ContractMapper.toDtoPage(page);

        return response;
    }

//    @Override
//    public Contract createContract(CreateContractRequest createContract) {
//          Contract contract = new Contract();
//          contract.setContractName(createContract.getContractName());
//          String contractFile = fileManagerService.saveUserContract(createContract.getContractFile());
//          contract.setFileName(contractFile);
//          contract.setUser(createContract.getUserId());
//          contractRepository.save(contract);
//          return contract;
//    }
    @Override
    public Contract createContract(CreateContractRequest createContract) throws NotFoundException {
        Contract contract = new Contract();
        contract.setContractName(createContract.getContractName());
        String contractFile = fileManagerService.saveUserContract(createContract.getContractFile());
        contract.setFileName(contractFile);
        Long userId = createContract.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " Not Found"));
        contract.setUser(user);
        contractRepository.save(contract);
        return contract;
    }

    @Override
    public ContractDto updateContract( long contractId, UpdateContractRequest updateContractRequest) throws NotFoundException {
        Contract c = contractRepository.findById(contractId).orElseThrow(() -> new NotFoundException("Position with id: " + contractId + " Not Found"));
        c.setContractName(updateContractRequest.getContractName());
        String contractFile = fileManagerService.saveUserContract(updateContractRequest.getContractFile());
        c.setContractName(contractFile);
        contractRepository.save(c);
        return ContractMapper.toDto(contractRepository.save(c));
    }
//    @Override
//    public Contract createContract(CreateContractRequest request) throws NotFoundException {
//        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new NotFoundException("User with id: " + request.getUserId() + " Not Found"));
//        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new NotFoundException("Department with id: " + request.getDepartmentId() + " Not Found"));
//
//        Contract contract = new Contract();
//        contract.setNameContract(request.getNameContract());
//        contract.setFileContract(request.getFileContract());
//        contract.setCreatedDate(request.getCreatedDate());
//        contract.setUpdatedDate(request.getUpdatedDate());
//        contract.setUser(user);
//        contract.getUser().setDepartment(department);
//
//        repository.save(contract);
//
//        return contract;
//    }
//    @Override
//    public Contract createContract(ContractDto contractDto){
//        Contract contract = ContractMapper.toEntity(contractDto);
//        Contract createdContract = contractRepository.save(contract);
//        return createdContract;
//    }
}
