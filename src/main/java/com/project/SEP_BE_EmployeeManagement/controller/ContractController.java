package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.ContractDto;
import com.project.SEP_BE_EmployeeManagement.dto.DepartmentDto;
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
import org.aspectj.weaver.ast.Instanceof;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

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
    public ResponseEntity<Page<ContractDto>> getContract(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "4") int  pageSize,
                                                         @RequestParam(name = "search", required = false, defaultValue = "") String search) {

        System.out.println(search);
        System.out.println(pageNo);
        System.out.println(pageSize);
        Page<ContractDto> response = contractService.getData(search, pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/data1")
    public ResponseEntity<Page<Contract>> getContract1(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "4") int  pageSize,
                                                         @RequestParam(name = "search", required = false, defaultValue = "") String search) {

        Page<Contract> response = contractService.getDataTest(search, pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ContractDto> getContrcatById(@PathVariable Integer id) throws NotFoundException {
        System.out.println("da chay vao de get du lieu");
        ContractDto response = contractService.getContractById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId")
    public ResponseEntity<Contract> getCurrentContract(@PathVariable long userId) throws NotFoundException {
        Contract contract = contractService.getCurrentContractByUserId(userId);
        return new ResponseEntity<>(contract, HttpStatus.OK);
    }

    @PostMapping("/create")
    public MessageResponse createContract(@RequestPart("createContractRequest") CreateContractRequest createContract
            , @RequestPart(value = "contractFile",required = false) MultipartFile contractFileR  ) throws NotFoundException {
        System.out.println("file nhan duoc la");
        System.out.println(contractFileR );

        System.out.println(createContract.getContractName());
        System.out.println(createContract.getUserId());



        Long userId = createContract.getUserId();
        if(contractFileR != null){
            System.out.println("da nhan file");
            createContract.setContractFile(contractFileR);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id: " + userId + " Not Found"));
        contractService.createContract(createContract);
        return new MessageResponse("Thêm hợp đồng thành công");
    }

    @PutMapping("/update/{contractId}")
    public MessageResponse updateContract(@PathVariable Long contractId, @RequestPart("updateContractRequest") CreateContractRequest updateContractRequest
            , @RequestPart(value = "contractFile",required = false) MultipartFile contractFileR) throws NotFoundException {

        System.out.println("file nhan  la1");
        System.out.println("ok:"+updateContractRequest.getContractName());
        System.out.println("ok:"+updateContractRequest.getUserId());
        Long userId = updateContractRequest.getUserId();
        if(contractFileR != null){
            System.out.println("da nhan file");
            System.out.println(contractFileR);
            updateContractRequest.setContractFile(contractFileR);
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " Not Found"));
        contractService.updateContract(contractId, updateContractRequest);
        return new MessageResponse("Cập nhật hợp đồng thành công");
    }

    @DeleteMapping(value = "delete/{contractId}")
    public MessageResponse deleteContract(@PathVariable("contractId") Long contractId) throws NotFoundException {
        contractService.deleteContract(contractId);
        return new MessageResponse("Xóa hợp đồng thành công");
    }

    @GetMapping("/employee-contact")
    public ResponseEntity<List<User>> getEmployee() {
        return new ResponseEntity<>(this.contractService.listEmployeeContact(), HttpStatus.OK);
    }

}
