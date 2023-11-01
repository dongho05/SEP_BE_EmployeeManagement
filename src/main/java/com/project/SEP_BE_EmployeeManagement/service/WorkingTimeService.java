package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.model.EWorkingTime;
import com.project.SEP_BE_EmployeeManagement.model.WorkingTime;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public interface WorkingTimeService {
    List<WorkingTime> getData ();
    WorkingTime getWorkingTimeById(int workingTimeId) throws NotFoundException;
    WorkingTime updateWorkingTime(int workingTimeId, LocalTime startTime, LocalTime endTime) throws NotFoundException;
}
