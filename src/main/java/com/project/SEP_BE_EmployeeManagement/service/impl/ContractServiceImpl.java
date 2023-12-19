package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.ContractDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.contract.CreateContractRequest;
import com.project.SEP_BE_EmployeeManagement.model.Contract;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.model.mapper.ContractMapper;
import com.project.SEP_BE_EmployeeManagement.repository.ContractRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.service.ContractService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

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
        Contract contract;
        if (contracts.size() > 0) {
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
    public Page<ContractDto> getData(String search, String deptId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Long idDept = Long.valueOf(deptId);
        Page<Contract> page = contractRepository.findAllByContractNameIsNotNull(search, idDept, pageable);
        System.out.println(page.getContent());
        System.out.println("test ok");

        Page<ContractDto> response = ContractMapper.toDtoPage(page);

        return response;
    }

    @Override
    public Page<Contract> getDataTest(String search, String deptId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        System.out.println("Data test: "+ search);
        System.out.println("Data test: "+ deptId);
        Long idDept = null;
        if (!deptId.isEmpty()) {
            idDept = Long.valueOf(deptId);
        }
        System.out.println("Data test: "+ deptId);
        Page<Contract> page = contractRepository.findAllByContractNameIsNotNull(search, idDept, pageable);
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
        if (updateContractRequest.getContractFile() == null) {
            System.out.println("file rong");
            contractFile = contract.getFileName();
        } else {
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
        ContractDto contractDto = ContractMapper.toDto(contractRepository.findById(id).orElseThrow(() -> new NotFoundException("Department with id: " + id + " Not Found")));
        return contractDto;
    }

    @Override
    public List<User> listEmployeeContact(String id) {
        //List<Long> listEmployeeID_DISTINCT = this.contractRepository.findDistinctUserIds();
       // System.out.println(listEmployeeID_DISTINCT);
        List<User> listEmployee = null;
        if(!id.isEmpty()){
            Long idLong = Long.valueOf(id);
            listEmployee = this.userRepository.findAllByDepartment_Id(idLong);
        }
        else {
            listEmployee = this.userRepository.findAll();
        }
        return listEmployee;
    }

}
