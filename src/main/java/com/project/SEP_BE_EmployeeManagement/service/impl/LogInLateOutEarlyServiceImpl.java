package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.model.LogInLateOutEarly;
import com.project.SEP_BE_EmployeeManagement.repository.LogInLateOutEarlyRepository;
import com.project.SEP_BE_EmployeeManagement.service.LogInLateOutEarlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class LogInLateOutEarlyServiceImpl implements LogInLateOutEarlyService {
    @Autowired
    private LogInLateOutEarlyRepository logInLateOutEarlyRepository;
    @Override
    public Page<LogInLateOutEarly> getData(String codeInput, String departmentIdInput, String searchInput, String fromInput, String toInput, Pageable pageable) {
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String code =  codeInput.toString() == null || codeInput.toString() == "" ? "" : codeInput;
        Integer departId = departmentIdInput == null || departmentIdInput == "" ? -1 : Integer.parseInt(departmentIdInput);
        String search = searchInput.toString() == null || searchInput.toString() == "" ? "" : searchInput;
        LocalDate fromDate = fromInput.toString() == null || fromInput.toString() == "" ?  LocalDate.of(1900,1,1)
                : LocalDate.parse(fromInput, sdf);
        LocalDate toDate = toInput.toString() == null || toInput.toString() == "" ?  LocalDate.now()
                : LocalDate.parse(toInput, sdf);

        Page<LogInLateOutEarly> list = logInLateOutEarlyRepository.getData(code, departId,search, fromDate,toDate,pageable);
        return list;
    }
}
