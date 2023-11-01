package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.response.attendance.AttendanceResponse;
import com.project.SEP_BE_EmployeeManagement.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/attendance")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @GetMapping("/get-list-attendance")
    public ResponseEntity<?> getList(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "30") int size,
                                    @RequestParam(name = "departmentId", defaultValue = "") String departmentId,
                                     @RequestParam(name = "from", defaultValue = "",required = false) String fromDate,
                                     @RequestParam(name = "to", defaultValue = "",required = false) String toDate) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttendanceResponse> pageRequests = attendanceService.getList( departmentId, fromDate, toDate, pageable);
        return ResponseEntity.ok(pageRequests);
    }

    @GetMapping("/get-list-attendance-by-user-id")
    public ResponseEntity<?> getListByUserId(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "30") int size,
                                     @RequestParam(name = "from", defaultValue = "",required = false) String fromDate,
                                     @RequestParam(name = "to", defaultValue = "",required = false) String toDate) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttendanceResponse> pageRequests = attendanceService.getListByUserId(fromDate, toDate, pageable);
        return ResponseEntity.ok(pageRequests);
    }
}
