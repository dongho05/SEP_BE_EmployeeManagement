package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.request.workingtime.UpdateWorkingTimeRequest;
import com.project.SEP_BE_EmployeeManagement.model.EWorkingTime;
import com.project.SEP_BE_EmployeeManagement.model.WorkingTime;
import com.project.SEP_BE_EmployeeManagement.service.WorkingTimeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/auth/workingtime")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WorkingTimeController {

    @Autowired
    private WorkingTimeService workingTimeService;

    @GetMapping("/data")
    public ResponseEntity<List<WorkingTime>> getWorkingTime(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
                                                            @RequestParam(name = "pageSize", defaultValue = "30") int pageSize,
                                                            @RequestParam(name = "search", required = false, defaultValue = "") String search){
        List<WorkingTime> result = workingTimeService.getData();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkingTime> getWorkingTimeById(@PathVariable int id) throws NotFoundException {
        WorkingTime result = workingTimeService.getWorkingTimeById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkingTime> updateWorkingTime(@PathVariable int id, @RequestBody UpdateWorkingTimeRequest request) throws NotFoundException {
        // Định dạng của chuỗi thời gian
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // Chuyển đổi chuỗi thành LocalTime
        LocalTime start = request.getStartTime();
        LocalTime end = request.getEndTime();
        WorkingTime result = workingTimeService.updateWorkingTime(id, start, end);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
