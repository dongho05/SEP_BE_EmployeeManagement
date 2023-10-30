package com.project.SEP_BE_EmployeeManagement.scheduled;

import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import com.project.SEP_BE_EmployeeManagement.model.ESign;
import com.project.SEP_BE_EmployeeManagement.model.Holiday;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExecuteAttendance {

    @Autowired
    HolidayRepository holidayRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    SignRepository signRepository;
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    LogCheckInOutRepository logCheckInOutRepository;

    public void ExecuteAttendance(){
        LocalDate currentDate = LocalDate.now();
        LocalDate executeDate = currentDate.minusDays(0);
        LocalDate firstDayOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);

        // Holiday CASE
        Holiday holidayYesterday = holidayRepository.findExecuteAttendanceDate(executeDate.toString());
        if (holidayYesterday != null) {
            List<User> users = userRepository.findAll();
            for (User user : users) {
                System.out.println(user.getUserCode() + " Add Log HOLIDAY");
                // create log
                Attendance attendance = new Attendance();
//                Set<NoteLog> noteCatergorySet = logDetail.getNoteLogSet();
//                if (noteCatergorySet == null)
//                    noteCatergorySet = new HashSet<>();
//                NoteLog noteLog = new NoteLog();
//                noteLog.setLogDetail(logDetail);
//                noteLog.setNoteCatergory(noteCatergoryRepository.findByName(ENoteCatergory.E_HOLIDAY));
//                noteLog.setContent(holidayYesterday.getHolidayName());
//                noteLog.setCreateDate(LocalDateTime.now());
//                noteLog.setSignChange(signRepository.findByName(ESign.L));
//                noteCatergorySet.add(noteLog);
//                attendance.setNoteLogSet(noteCatergorySet);

                attendance.setSigns(signRepository.findByName(ESign.L));
                attendance.setDateLog(executeDate);
                attendance.setUser(user);
//                logDetail.setRequestActive(true);
                attendanceRepository.save(attendance);
            }
            return;
        }
    }
}
