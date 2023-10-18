package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.model.LogInLateOutEarly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LogInLateOutEarlyService {
    Page<LogInLateOutEarly> getData(String code, String departmentId, String searchInput, String from, String to, Pageable pageable);

}
