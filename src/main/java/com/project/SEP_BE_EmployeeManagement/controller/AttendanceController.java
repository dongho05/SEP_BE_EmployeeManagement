package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.UserAttendance;
import com.project.SEP_BE_EmployeeManagement.dto.request.attendance.EditAttendance;
import com.project.SEP_BE_EmployeeManagement.dto.response.MessageResponse;
import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.repository.AttendanceRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AttendanceController {
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AttendanceService attendanceService;

    @PostMapping("/startEditing/{attendanceId}/{userId}")
    public ResponseEntity<String> startEditing(@PathVariable Long attendanceId, @PathVariable Long userId) {
        attendanceService.startEditing(attendanceId, userId);
        return ResponseEntity.ok("Editing started successfully");
    }

    @PostMapping("/finishEditing/{attendanceId}")
    public ResponseEntity<String> finishEditing(@PathVariable Long attendanceId) {
        attendanceService.finishEditing(attendanceId);
        return ResponseEntity.ok("Editing finished successfully");
    }
    @GetMapping("getAttendanceByMonthAndYear")
    public ResponseEntity<List<Attendance>> getByUserAndMonthAndYear(@RequestParam String code,
                                                                     @RequestParam Integer year
    )
    {
        return new ResponseEntity<>(attendanceRepository.findByUserCodeAndMonthAndYear(code,year), HttpStatus.OK);
    }

    @GetMapping("allByMonthAndYearAndDepartment")
    public ResponseEntity<List<UserAttendance>> getAllByMonthAndDepartment(
            @RequestParam(name = "month", required = true) Integer month,
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "search",required = false) String search,
            @RequestParam(name = "year",required = true) Integer year
    ) throws ParseException {
        List<Attendance> attendances = null;
        List<User> users = userRepository.findAll();
        List<UserAttendance> userAttendanceList = new ArrayList<>();
        if (id != null) {
            List<User> usersDepart = userRepository.findAllByDepartmentId(id);
            attendances = attendanceRepository.findByMonthAndDepartment(id, month,year,search);
            for (User user : usersDepart) {
                UserAttendance userAttendance = new UserAttendance();
                List<Attendance> list = new ArrayList<>();
                for (Attendance attendance : attendances) {
                    if (attendance.getUser() == user) {
                        list.add(attendance);
                    } else continue;
                }
                userAttendance.setCode(user.getUserCode());
                userAttendance.setName(user.getFullName());
                userAttendance.setLogDetail(list);
                if(search.equals("") )
                    userAttendanceList.add(userAttendance);
                else{
                    if(!userAttendance.getLogDetail().isEmpty()){
                        userAttendanceList.add(userAttendance);
                    }
                }
                continue;
            }
        } else {
            attendances = attendanceRepository.findByMonth(month,year,search);
            for (User user : users) {
                UserAttendance userAttendance = new UserAttendance();
                List<Attendance> list = new ArrayList<>();
                for (Attendance attendance : attendances) {
                    if (attendance.getUser() == user) {
                        list.add(attendance);
                    } else continue;
                }
                userAttendance.setCode(user.getUserCode());
                userAttendance.setName(user.getFullName());
                userAttendance.setLogDetail(list);
                if(search.equals("") )
                    userAttendanceList.add(userAttendance);
                else{
                    if(!userAttendance.getLogDetail().isEmpty()){
                        userAttendanceList.add(userAttendance);
                    }
                }
                continue;
            }

        }
        return new ResponseEntity<>(userAttendanceList, HttpStatus.OK);
    }

    @PostMapping("edit")
    public ResponseEntity<MessageResponse> updateLogSign(@Valid @RequestBody EditAttendance[] editAttendances) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(attendanceService.updateAttendance(editAttendances));
    }
}
