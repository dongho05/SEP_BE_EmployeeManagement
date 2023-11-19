package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.ContractDto;
import com.project.SEP_BE_EmployeeManagement.dto.DepartmentDto;
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
import com.project.SEP_BE_EmployeeManagement.model.mapper.DepartmentMapper;
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

import java.time.Instant;
import java.util.Date;
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
    public Page<ContractDto> getData(String search, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Contract> page = contractRepository.findAllByContractNameIsNotNull(search,pageable);
        System.out.println(page.getContent());
        System.out.println("test ok");

        Page<ContractDto> response = ContractMapper.toDtoPage(page);

        return response;
    }

    @Override
    public Page<Contract> getDataTest(String search, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Contract> page = contractRepository.findAllByContractNameIsNotNull(search,pageable);
        return page;
    }

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
    public Contract updateContract(Long contractId, CreateContractRequest updateContractRequest) throws NotFoundException {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new NotFoundException("Contract with id: " + contractId + " Not Found"));
        contract.setContractName(updateContractRequest.getContractName());
        String contractFile = "";
        if(updateContractRequest.getContractFile() == null){
            System.out.println("file rong");
            contractFile = contract.getFileName();
        }
        else {
            contractFile = fileManagerService.saveUserContract(updateContractRequest.getContractFile());
        }
        contract.setFileName(contractFile);
        Long userId = updateContractRequest.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " Not Found"));
        contract.setUser(user);
        contractRepository.save(contract);
        return contract;
    }

    @Override
    public void deleteContract(Long contractId) throws NotFoundException {
        Contract contract = contractRepository.findById(contractId).orElseThrow(() -> new NotFoundException("Contract with id: " + contractId + " Not Found"));
        contractRepository.delete(contract);
    }
    @Override
    public ContractDto getContractById(long id) throws NotFoundException {
        ContractDto contractDto = ContractMapper.toDto(contractRepository.findById(id).orElseThrow(() -> new NotFoundException("Department with id: " + id + " Not Found"))) ;
        return contractDto;
    }

    @Override
    public List<User> listEmployeeContact() {
        List<Long> listEmployeeID_DISTINCT = this.contractRepository.findDistinctUserIds();
        System.out.println(listEmployeeID_DISTINCT);
        List<User> listEmployee_conlai = this.userRepository.findAllByIdNotIn(listEmployeeID_DISTINCT);
        return listEmployee_conlai;
    }
}
