package com.project.SEP_BE_EmployeeManagement.controller;


import com.project.SEP_BE_EmployeeManagement.model.LogCheckInOut;
import com.project.SEP_BE_EmployeeManagement.service.LogCheckInOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth/logCheckInOut")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LogCheckInOutController {
    @Autowired
    LogCheckInOutService logCheckInOutService;

    @GetMapping("/data")
    public ResponseEntity<Page<LogCheckInOut>> getLogsByDateUserCode(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                     @RequestParam(name = "size", defaultValue = "30") int size,
                                                                     @RequestParam(name = "code", required = false) String code,
                                                                     @RequestParam(name = "departmentId", required = false) String departmentId,
                                                                     @RequestParam(name = "search", required = false) String search,
                                                                     @RequestParam(name = "from", required = false) String from,
                                                                     @RequestParam(name = "to", required = false) String to) throws ParseException {
        Pageable pageable = PageRequest.of(page, size);
        Page<LogCheckInOut> pageLogs = logCheckInOutService.getData(code, departmentId,search, from, to, pageable);

        return new ResponseEntity<>(pageLogs, HttpStatus.OK);
    }
}
