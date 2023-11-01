package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.model.*;
import com.project.SEP_BE_EmployeeManagement.repository.*;
import com.project.SEP_BE_EmployeeManagement.security.jwt.UserDetailsImpl;
import com.project.SEP_BE_EmployeeManagement.service.AttendanceService;
import com.project.SEP_BE_EmployeeManagement.service.HolidayService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private LogCheckInOutRepository logCheckInOutRepository;

    @Autowired
    private LogInLateOutEarlyRepository logInLateOutEarlyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @Autowired
    private HolidayService holidayService;

    @Override
    @Scheduled(cron = "0 0 22 * * ?") // process attendance vào 22h hàng ngày
    public List<Attendance> processAttendanceForUserOnDate() throws NotFoundException {

        LocalDate date = LocalDate.of(2023, 10, 26);

//        LocalDate date = LocalDate.now();

        List<Attendance> attendanceList = new ArrayList<>();

        // kiểm tra ngày hôm đó có phải là ngày cuối tuần hay không
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY){
            List<User> userList = userRepository.findAll();
            // kiểm tra ngày cuối tuần đó có phải là ngày nghỉ lễ hay không
            if(holidayService.isDateHoliday(date)){
                for(User u : userList){
                    Attendance attendance = new Attendance(u, date);
                    attendance.setSigns(new Sign(ESign.L));
                    attendanceRepository.save(attendance);
                    attendanceList.add(attendance);
                }
            }else{
                for(User u : userList){
                    Attendance attendance = new Attendance(u, date);
                    attendance.setSigns(new Sign(ESign.NT));
                    attendanceRepository.save(attendance);
                    attendanceList.add(attendance);
                }
            }
        }
        // kiểm tra ngày hôm đó có phải là ngày nghỉ lễ hay không
        else if(holidayService.isDateHoliday(date)){
            List<User> userList = userRepository.findAll();
            for(User u : userList){
                Attendance attendance = new Attendance(u, date);
                attendance.setSigns(new Sign(ESign.L));
                attendanceRepository.save(attendance);
                attendanceList.add(attendance);
            }
        }
        // trường hợp không phải là ngày cuối tuần và ngày lễ
        else{
            List<Long> listUserId = logCheckInOutRepository.findDistinctUserIdByDateCheck(date);
            WorkingTime morningShift = workingTimeRepository.findByWorkingTimeName(EWorkingTime.MORNING_SHIFT).orElseThrow();
            WorkingTime afternoonShift = workingTimeRepository.findByWorkingTimeName(EWorkingTime.AFTERNOON_SHIFT).orElseThrow();
            for(Long i : listUserId){
                User user = userRepository.findById(i).orElseThrow();
                List<LogCheckInOut> checkInOutRecords = logCheckInOutRepository.findByUserAndDateCheck(i, date);
                if (checkInOutRecords.size() > 0) {
                    // lưu vào attendance
                    LocalTime earliestCheckIn = checkInOutRecords.get(0).getTimeCheck();
                    LocalTime latestCheckOut = checkInOutRecords.get(checkInOutRecords.size() - 1).getTimeCheck();
                    Attendance attendance = new Attendance(user, date);
                    // set timein timeout
                    // nếu checkin trước giờ checkin mặc định thì giờ checkin = giờ checkin mặc định
                    if(earliestCheckIn.isBefore(morningShift.getStartTime())){
                        attendance.setTimeIn(morningShift.getStartTime());
                    }
                    // nếu checkin sau giờ checkout ca sáng và trước giờ checkin ca chiều thì giờ checkin = giờ checkin mặc định ca chiều
                    else if(earliestCheckIn.isAfter(morningShift.getEndTime()) && earliestCheckIn.isBefore(afternoonShift.getStartTime())){
                        attendance.setTimeIn(afternoonShift.getStartTime());
                    }
                    // nếu checkin sau giờ checkout ca chiều thì giờ checkin = giờ checkout ca chiều
                    else if(earliestCheckIn.isAfter(afternoonShift.getEndTime())){
                        attendance.setTimeIn(afternoonShift.getEndTime());
                    }
                    else {
                        attendance.setTimeIn(earliestCheckIn);
                    }
                    // nếu checkout sau giờ checkout mặc định thì giờ checkout = giờ checkout mặc định
                    if(latestCheckOut.isAfter(afternoonShift.getEndTime())){
                        attendance.setTimeOut(afternoonShift.getEndTime());
                    }
                    // nếu checkout sau giờ checkout ca sáng và trước giờ checkin ca chiều thì giờ checkout = giờ checkout mặc định ca sáng
                    else if(!latestCheckOut.isBefore(morningShift.getEndTime()) && latestCheckOut.isBefore(afternoonShift.getStartTime())){
                        attendance.setTimeOut(morningShift.getEndTime());
                    }else{
                        attendance.setTimeOut(latestCheckOut);
                    }
                    // set RegularHour
                    // nếu checkin trong giờ làm ca sáng thì RegularHour phải trừ thời gian nghỉ trưa
                    if(attendance.getTimeIn().isBefore(morningShift.getEndTime())){
                        LocalTime morning = morningShift.getEndTime()
                                .minusHours(attendance.getTimeIn().getHour())
                                .minusMinutes(attendance.getTimeIn().getMinute())
                                .minusSeconds(attendance.getTimeIn().getSecond());
                        LocalTime afternoon = attendance.getTimeOut()
                                .minusHours(afternoonShift.getStartTime().getHour())
                                .minusMinutes(afternoonShift.getStartTime().getMinute())
                                .minusSeconds(afternoonShift.getStartTime().getSecond());
                        LocalTime regularHour = morning.plusHours(afternoon.getHour())
                                .plusMinutes(afternoon.getMinute())
                                .plusSeconds(afternoon.getSecond());
                        attendance.setRegularHour(regularHour);
                        attendance.setSigns(new Sign(ESign.H));
                    }
                    // nếu checkin trong giờ làm ca chiều thì RegularHour không phải trừ thời gian nghỉ trưa
                    if(!attendance.getTimeOut().isAfter(afternoonShift.getStartTime())){
                        LocalTime regularHour = attendance.getTimeOut()
                                .minusHours(attendance.getTimeIn().getHour())
                                .minusMinutes(attendance.getTimeIn().getMinute())
                                .minusSeconds(attendance.getTimeIn().getSecond());
                        attendance.setRegularHour(regularHour);
                        attendance.setSigns(new Sign(ESign.H_P));
                    }
                    if(attendance.getOverTime() == null){
                        attendance.setTotalWork(attendance.getRegularHour());
                    }else{
                        attendance.setTotalWork(attendance.getRegularHour()
                                .plusHours(attendance.getOverTime().getHour())
                                .plusMinutes(attendance.getOverTime().getMinute())
                                .plusSeconds(attendance.getOverTime().getSecond()));
                    }
                    attendanceRepository.save(attendance);
                    attendanceList.add(attendance);

                    // lưu vào LogInLateOutEarly
                    if(attendance.getTimeIn().isAfter(morningShift.getStartTime())){
                        LogInLateOutEarly logInLateOutEarly = new LogInLateOutEarly();
                        logInLateOutEarly.setUser(user);
                        logInLateOutEarly.setDateCheck(date);
                        logInLateOutEarly.setIOKind("LATE_CHECKIN");
                        logInLateOutEarly.setTimeStart(attendance.getTimeIn());
                        logInLateOutEarly.setTimeEnd(attendance.getTimeOut());
                        LocalTime duration = logInLateOutEarly.getTimeStart()
                                .minusHours(morningShift.getStartTime().getHour())
                                .minusMinutes(morningShift.getStartTime().getMinute())
                                .minusSeconds(morningShift.getStartTime().getSecond());
                        logInLateOutEarly.setDuration(duration);
                        logInLateOutEarlyRepository.save(logInLateOutEarly);
                    }
                    if(attendance.getTimeOut().isBefore(afternoonShift.getEndTime())){
                        LogInLateOutEarly logInLateOutEarly = new LogInLateOutEarly();
                        logInLateOutEarly.setUser(user);
                        logInLateOutEarly.setDateCheck(date);
                        logInLateOutEarly.setIOKind("EARLY_CHECKOUT");
                        logInLateOutEarly.setTimeStart(attendance.getTimeIn());
                        logInLateOutEarly.setTimeEnd(attendance.getTimeOut());
                        LocalTime duration = afternoonShift.getEndTime()
                                .minusHours(logInLateOutEarly.getTimeEnd().getHour())
                                .minusMinutes(logInLateOutEarly.getTimeEnd().getMinute())
                                .minusSeconds(logInLateOutEarly.getTimeEnd().getSecond());
                        logInLateOutEarly.setDuration(duration);
                        logInLateOutEarlyRepository.save(logInLateOutEarly);
                    }
                }
            }
        }
        return attendanceList;
    }

    @Override
    public List<Attendance> findAttendancesForUserInMonth(int year, int month) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return attendanceRepository.findAttendancesForUserInMonth(userDetails.getId(), year, month);
    }
}
