package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/position")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PositionController {
    @Autowired
    PositionService positionService;
    @GetMapping("/get-all")
    public ResponseEntity<?> GetAll(){
        return ResponseEntity.ok(positionService.GetAll());
    }
}
