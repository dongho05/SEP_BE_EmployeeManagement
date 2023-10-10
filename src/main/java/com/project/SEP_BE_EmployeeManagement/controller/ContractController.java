package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.UserDto;
import com.project.SEP_BE_EmployeeManagement.model.Contract;
import com.project.SEP_BE_EmployeeManagement.service.ContractService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contract")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContractController {
    @Autowired
    private ContractService contractService;

    @GetMapping("/{userId}")
    public ResponseEntity<Contract> getCurrentContract(@PathVariable long userId) throws NotFoundException {
        Contract contract = contractService.getCurrentContractByUserId(userId);
        return new ResponseEntity<>(contract, HttpStatus.OK);
    }
}
