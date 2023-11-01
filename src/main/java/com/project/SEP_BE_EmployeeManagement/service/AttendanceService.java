package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.response.Attendance.AttendanceStatistics;
import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface AttendanceService {
    public List<Attendance> processAttendanceForUserOnDate() throws NotFoundException;

    public List<Attendance> findAttendancesForUserInMonth(int year, int month);

    public List<AttendanceStatistics> getAttendanceStatisticsOnMonth(int month, int year);
}
