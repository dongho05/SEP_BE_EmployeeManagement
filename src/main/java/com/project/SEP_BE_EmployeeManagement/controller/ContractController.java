package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.ContractDto;
import com.project.SEP_BE_EmployeeManagement.dto.PositionDto;
import com.project.SEP_BE_EmployeeManagement.dto.UserDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.CreateUser;
import com.project.SEP_BE_EmployeeManagement.dto.request.User.UpdateUserRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.contract.CreateContractRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.contract.UpdateContractRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.MessageResponse;
import com.project.SEP_BE_EmployeeManagement.model.Contract;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.service.ContractService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/contract")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContractController {

    @Autowired
    private ContractService contractService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/get-all")
    public ResponseEntity<?> GetAll() {
        return ResponseEntity.ok(contractService.GetAll());
    }

    @GetMapping("/data")
    public ResponseEntity<Page<ContractDto>> getContract(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "30") int pageSize,
                                                         @RequestParam(name = "search", required = false, defaultValue = "") String search) {
        Page<ContractDto> response = contractService.getData(search, pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Contract> getCurrentContract(@PathVariable long userId) throws NotFoundException {
        Contract contract = contractService.getCurrentContractByUserId(userId);
        return new ResponseEntity<>(contract, HttpStatus.OK);
    }

//    @PostMapping(value = "create")
//    public MessageResponse createContract(@Valid @ModelAttribute CreateContractRequest createContract) throws MessagingException, UnsupportedEncodingException, NotFoundException {
//
////            for(CreateUser s : createUser){
////                userService.createUser(s);
////            }
//        contractService.createContract(createContract);
//        return new MessageResponse("Thêm hợp đồng thành công");
//
//    }
    @PostMapping(value = "create")
    public MessageResponse createContract(@Valid @ModelAttribute CreateContractRequest createContract) throws MessagingException, UnsupportedEncodingException, NotFoundException {
        Long userId = createContract.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id: " + userId + " Not Found"));
        contractService.createContract(createContract);
        return new MessageResponse("Thêm hợp đồng thành công");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ContractDto> updateContract( @PathVariable long contractId, @ModelAttribute UpdateContractRequest request) throws NotFoundException {
        ContractDto contractDto = contractService.updateContract(contractId, request);
        return new ResponseEntity<>(contractDto, HttpStatus.OK);

//    @PostMapping("/create")
//    public ResponseEntity<Contract> createContract(@RequestBody ContractDto contractDto) {
//        Contract createdContract = contractService.createContract(contractDto);
//        return new ResponseEntity<>(createdContract, HttpStatus.OK);
//    }
//    @PostMapping("/create")
//    public ResponseEntity<Contract> createContract(@RequestBody CreateContractRequest request) throws NotFoundException {
//        Contract response = contractService.createContract(request);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
    }
}
