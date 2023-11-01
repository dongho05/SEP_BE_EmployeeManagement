package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.model.EWorkingTime;
import com.project.SEP_BE_EmployeeManagement.model.WorkingTime;
import com.project.SEP_BE_EmployeeManagement.repository.WorkingTimeRepository;
import com.project.SEP_BE_EmployeeManagement.service.WorkingTimeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class WorkingTimeServiceImpl implements WorkingTimeService {

    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @Override
    public List<WorkingTime> getData() {
        List<WorkingTime> result = workingTimeRepository.findAll();
        return result;
    }

    @Override
    public WorkingTime getWorkingTimeById(int workingTimeId) throws NotFoundException {
        WorkingTime workingTime = workingTimeRepository.findByWorkingId(workingTimeId).orElseThrow(() -> new NotFoundException("Working Time with id: " + workingTimeId + " Not Found"));;
        return workingTime;
    }

    @Override
    public WorkingTime updateWorkingTime(int workingTimeId, LocalTime startTime, LocalTime endTime) throws NotFoundException {
        WorkingTime workingTime = workingTimeRepository.findByWorkingId(workingTimeId).orElseThrow(() -> new NotFoundException("Working Time with id: " + workingTimeId + " Not Found"));
        workingTime.setEndTime(endTime);
        workingTime.setStartTime(startTime);
        workingTimeRepository.save(workingTime);
        return workingTime;
    }
}
