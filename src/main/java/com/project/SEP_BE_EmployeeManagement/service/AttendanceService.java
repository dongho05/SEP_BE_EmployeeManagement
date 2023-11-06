package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.response.attendance.AttendanceStatistics;
import com.project.SEP_BE_EmployeeManagement.dto.response.attendance.AttendanceResponse;
import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import com.project.SEP_BE_EmployeeManagement.model.ESign;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttendanceService {
    public List<Attendance> processAttendanceForUserOnDate() throws NotFoundException;

    public List<Attendance> findAttendancesForUserInMonth(int year, int month);

    public Page<AttendanceStatistics> getAttendanceStatisticsOnMonth(int month, int year, Pageable pageable);
    Page<AttendanceResponse> getList(String departmentId, String fromDate, String toDate, Pageable pageable);
    Page<AttendanceResponse> getListByUserId(String fromDate, String toDate, Pageable pageable);

    Attendance getByAttendanceId(Long id);

    Attendance updateSigns(ESign signId, Long attendanceId, String reason);
}
