package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.model.LogAttendanceHistory;
import com.project.SEP_BE_EmployeeManagement.repository.LogAttendanceHistoryRepository;
import com.project.SEP_BE_EmployeeManagement.service.LogAttendanceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LogAttendanceHistoryServiceImpl implements LogAttendanceHistoryService {
    @Autowired
    LogAttendanceHistoryRepository logAttendanceHistoryRepository;
    @Override
    public Page<LogAttendanceHistory> getList(Pageable pageable) {
        Page<LogAttendanceHistory> list = logAttendanceHistoryRepository.getList(pageable);
        return list;
    }

    @Override
    public LogAttendanceHistory save(LogAttendanceHistory entity) {
        logAttendanceHistoryRepository.save(entity);
        return entity;
    }
}
