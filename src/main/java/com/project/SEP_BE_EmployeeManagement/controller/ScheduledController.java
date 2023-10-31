package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.response.connector.TblInLateOutEarly;
import com.project.SEP_BE_EmployeeManagement.dto.response.connector.TmpCheckInOut;
import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import com.project.SEP_BE_EmployeeManagement.scheduled.CallApi;
import com.project.SEP_BE_EmployeeManagement.service.AttendanceService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scheduled/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ScheduledController {

    @Autowired
    CallApi callApi;

    @Autowired
    private AttendanceService attendanceService;


    @GetMapping("getCheckInOut")
    public List<TmpCheckInOut> getCheckInOut(){
        List<TmpCheckInOut> tmpCheckInOutList =
                callApi.getLogCheckInOut();
        return tmpCheckInOutList;
    }

    @GetMapping("getInLateOutEarly")
    public List<TblInLateOutEarly> getInLateOutEarly(){
        List<TblInLateOutEarly> tblInLateOutEarlies =
                callApi.getLogInLateOutEarly();
        return tblInLateOutEarlies;
    }

    @GetMapping("processAttendance")
    public List<Attendance> attendanceToDay() throws NotFoundException {
        List<Attendance> attendanceList = attendanceService.processAttendanceForUserOnDate();
        return attendanceList;
    }
}
