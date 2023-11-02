package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.response.attendance.AttendanceResponse;
import com.project.SEP_BE_EmployeeManagement.model.LogAttendanceHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface LogAttendanceHistoryService {
    Page<LogAttendanceHistory> getList(Pageable pageable);

    LogAttendanceHistory save(LogAttendanceHistory entity);
}
