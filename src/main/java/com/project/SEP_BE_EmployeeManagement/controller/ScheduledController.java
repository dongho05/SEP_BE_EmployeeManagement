package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.response.attendance.AttendanceStatistics;
import com.project.SEP_BE_EmployeeManagement.dto.response.connector.TblInLateOutEarly;
import com.project.SEP_BE_EmployeeManagement.dto.response.connector.TmpCheckInOut;
import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import com.project.SEP_BE_EmployeeManagement.scheduled.CallApi;
import com.project.SEP_BE_EmployeeManagement.service.AttendanceService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<List<Attendance>> attendanceToDay() throws NotFoundException {
        List<Attendance> attendanceList = attendanceService.processAttendanceForUserOnDate();
        return new ResponseEntity<>(attendanceList, HttpStatus.OK);
    }

    @GetMapping("viewAttendance")
    public ResponseEntity<List<Attendance>> findAttendancesForUserInMonth(@RequestParam(name = "year", defaultValue = "0") int year,
                                                                          @RequestParam(name = "month", defaultValue = "0") int month){
        if (year == 0) {
            year = LocalDate.now().getYear();
        }
        if (month == 0) {
            month = LocalDate.now().getMonthValue();
        }
        List<Attendance> attendanceList = attendanceService.findAttendancesForUserInMonth(year, month);
        return new ResponseEntity<>(attendanceList, HttpStatus.OK);
    }

    @GetMapping("/attendanceStatistics")
    public ResponseEntity<Page<AttendanceStatistics>> getAttendanceStatistics(@RequestParam(name = "year", defaultValue = "0") int year,
                                                                              @RequestParam(name = "month", defaultValue = "0") int month,
                                                                              @RequestParam(name = "page", defaultValue = "0") int page,
                                                                              @RequestParam(name = "size", defaultValue = "30") int size){
        Pageable pageable = PageRequest.of(page, size);
        if (year == 0) {
            year = LocalDate.now().getYear();
        }
        if (month == 0) {
            month = LocalDate.now().getMonthValue();
        }
        Page<AttendanceStatistics> rerult = attendanceService.getAttendanceStatisticsOnMonth(month, year, pageable);
        return new ResponseEntity<>(rerult, HttpStatus.OK);
    }
}
