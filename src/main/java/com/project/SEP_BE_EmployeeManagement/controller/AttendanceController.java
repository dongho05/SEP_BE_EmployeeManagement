package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.UserAttendance;
import com.project.SEP_BE_EmployeeManagement.dto.request.attendance.AttendanceRequestDTO;
import com.project.SEP_BE_EmployeeManagement.dto.request.attendance.EditAttendance;
import com.project.SEP_BE_EmployeeManagement.dto.request.attendance.UpdateSignInAttendanceRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.MessageResponse;
import com.project.SEP_BE_EmployeeManagement.dto.response.attendance.AttendanceResponse;
import com.project.SEP_BE_EmployeeManagement.exportExcel.ExcelExporterReport;
import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.repository.AttendanceRepository;
import com.project.SEP_BE_EmployeeManagement.repository.DepartmentRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/api/auth/attendance")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AttendanceController {
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentRepository departmentRepository;


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
    @GetMapping("/getAttendanceByMonthAndYear")
    public ResponseEntity<List<Attendance>> getByUserAndMonthAndYear(@RequestParam String code,
                                                                     @RequestParam Integer year
    )
    {
        return new ResponseEntity<>(attendanceService.getForCalendar(code, year), HttpStatus.OK);
    }

    @GetMapping("/allByMonthAndYearAndDepartment")
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
                LocalTime otHour = LocalTime.of(0, 0, 0);
                for (Attendance attendance : attendances) {
                    if (attendance.getUser() == user) {
                        list.add(attendance);
                        if(attendance.getOverTime()!=null){
                            otHour = attendance.getOverTime().plusHours(otHour.getHour())
                                    .plusMinutes(otHour.getMinute())
                                    .plusSeconds(otHour.getSecond());
                        }
                    } else continue;
                }
                userAttendance.setCode(user.getUserCode());
                userAttendance.setName(user.getFullName());
                userAttendance.setAttendances(list);
                userAttendance.setOtHour(otHour);
                if(search.equals("") )
                    userAttendanceList.add(userAttendance);
                else{
                    if(!userAttendance.getAttendances().isEmpty()){
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
                LocalTime otHour = LocalTime.of(0, 0, 0);
                for (Attendance attendance : attendances) {
                    if (attendance.getUser() == user) {
                        list.add(attendance);
                        if(attendance.getOverTime()!=null){
                            otHour = attendance.getOverTime().plusHours(otHour.getHour())
                                    .plusMinutes(otHour.getMinute())
                                    .plusSeconds(otHour.getSecond());
                        }

                    } else continue;
                }
                userAttendance.setCode(user.getUserCode());
                userAttendance.setName(user.getFullName());
                userAttendance.setAttendances(list);
                userAttendance.setOtHour(otHour);
                if(search.equals("") )
                    userAttendanceList.add(userAttendance);
                else{
                    if(!userAttendance.getAttendances().isEmpty()){
                        userAttendanceList.add(userAttendance);
                    }
                }
                continue;
            }

        }
        return new ResponseEntity<>(userAttendanceList, HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<MessageResponse> updateLogSign(@Valid @RequestBody EditAttendance[] editAttendances) throws MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(attendanceService.updateAttendance(editAttendances));
    }
    // them tim kiem ho ten nhan vien voi ma nhan vien
    @GetMapping("/get-list-attendance")
    public ResponseEntity<?> getList(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "30") int size,
                                     @RequestParam(name = "departmentId", defaultValue = "") String departmentId,
                                     @RequestParam(name = "searchInput", defaultValue = "") String searchInput,
                                     @RequestParam(name = "from", defaultValue = "",required = false) String fromDate,
                                     @RequestParam(name = "to", defaultValue = "",required = false) String toDate) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttendanceResponse> pageRequests = attendanceService.getList( departmentId, searchInput, fromDate, toDate, pageable);
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

    @GetMapping("/export_report")
    public ResponseEntity exportToExcel(@RequestParam(name = "id", defaultValue = "0") Long id, @RequestParam int month, @RequestParam(name = "year",required = true) Integer year, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Bang Cham Cong " + currentDateTime + ".xlsx";

        response.setHeader(headerKey, headerValue);
        List<Attendance> listLogs = new ArrayList<>();
        if(id==0){
            listLogs = attendanceRepository.findByMonthSortDate(month, year);
            ExcelExporterReport excelExporter = new ExcelExporterReport(listLogs,month,departmentRepository,userRepository,attendanceRepository);
            excelExporter.export(response);
        }
        else {
            listLogs = attendanceRepository.findByMonthAndDepartmentSortDate(id, month,year);
            ExcelExporterReport excelExporter = new ExcelExporterReport(listLogs, id,month,departmentRepository,userRepository,attendanceRepository);
            excelExporter.export(response);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/get-attendance-by-user-and-date-log")
    public ResponseEntity<?> getAttendanceByUserIdAndDateLog(@RequestBody AttendanceRequestDTO requestDTO) throws Exception {
        return ResponseEntity.ok(attendanceService.getAttendanceByUserIdAndDateLog(requestDTO.getDateLog()));
    }
}
