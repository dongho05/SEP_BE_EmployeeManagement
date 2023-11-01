package com.project.SEP_BE_EmployeeManagement.dto.response.Attendance;

import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import com.project.SEP_BE_EmployeeManagement.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceStatistics {
    private User user;
    List<Attendance> attendanceList;
    int workingDay;
    int salaryDay;
}
