package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.response.attendance.AttendanceResponse;
import com.project.SEP_BE_EmployeeManagement.model.LogAttendanceHistory;
import com.project.SEP_BE_EmployeeManagement.service.LogAttendanceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/attendance")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AttendanceHistoryController {
    @Autowired
    LogAttendanceHistoryService logAttendanceHistoryService;

    @GetMapping("/get-list-log-attendance-history")
    public ResponseEntity<?> getList(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "30") int size
                                     ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LogAttendanceHistory> pageRequests = logAttendanceHistoryService.getList(pageable);
        return ResponseEntity.ok(pageRequests);
    }
}
