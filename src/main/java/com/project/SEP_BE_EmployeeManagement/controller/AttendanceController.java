package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.UserAttendance;
import com.project.SEP_BE_EmployeeManagement.dto.request.attendance.UpdateSignInAttendanceRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.attendance.AttendanceResponse;
import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.repository.AttendanceRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    AttendanceRepository attendanceRepository;

    @GetMapping("/get-list-attendance")
    public ResponseEntity<?> getList(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "30") int size,
                                    @RequestParam(name = "departmentId", defaultValue = "") String departmentId,
                                     @RequestParam(name = "from", defaultValue = "",required = false) String fromDate,
                                     @RequestParam(name = "to", defaultValue = "",required = false) String toDate) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttendanceResponse> pageRequests = attendanceService.getList( departmentId, fromDate, toDate, pageable);
        return ResponseEntity.ok(pageRequests);
    }

    @GetMapping("/get-list-attendance-by-user-id")
    public ResponseEntity<?> getListByUserId(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "30") int size,
                                     @RequestParam(name = "from", defaultValue = "",required = false) String fromDate,
                                     @RequestParam(name = "to", defaultValue = "",required = false) String toDate) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttendanceResponse> pageRequests = attendanceService.getListByUserId(fromDate, toDate, pageable);
        return ResponseEntity.ok(pageRequests);
    }

    @PutMapping("/update-signs/{attendanceId}")
    public ResponseEntity<?> updateSigns(@RequestBody UpdateSignInAttendanceRequest request, @PathVariable Long attendanceId){
        Attendance obj = attendanceService.getByAttendanceId(attendanceId);
        if(obj == null){
            return ResponseEntity.internalServerError().body("Đã xảy ra lỗi: Không tồn tại chấm công này.");
        }
        attendanceService.updateSigns(request.getSign(), attendanceId, request.getReason());
        return ResponseEntity.ok(obj);
    }

    @Autowired
    UserRepository userRepository;
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
}
