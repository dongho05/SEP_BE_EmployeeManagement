package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.response.attendance.AttendanceStatistics;
import com.project.SEP_BE_EmployeeManagement.dto.response.attendance.AttendanceResponse;
import com.project.SEP_BE_EmployeeManagement.model.*;
import com.project.SEP_BE_EmployeeManagement.repository.*;
import com.project.SEP_BE_EmployeeManagement.security.jwt.UserDetailsImpl;
import com.project.SEP_BE_EmployeeManagement.service.AttendanceService;
import com.project.SEP_BE_EmployeeManagement.service.HolidayService;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import javassist.NotFoundException;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

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

    @Autowired
    private UserService userService;

    @Override
    @Scheduled(cron = "0 0 22 * * ?") // process attendance vào 22h hàng ngày
    public List<Attendance> processAttendanceForUserOnDate() throws NotFoundException {

        LocalDate date = LocalDate.of(2023, 10, 26);
//        LocalDate date = LocalDate.now();
        List<Attendance> attendanceList = new ArrayList<>();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        // kiểm tra ngày hôm đó có phải là ngày nghỉ lễ hay không
        if (holidayService.isDateHoliday(date)) {
            List<User> userList = userRepository.findAll();
            for (User u : userList) {
                Attendance attendance = new Attendance(u, date);
                attendance.setSigns(new Sign(ESign.L));
                attendanceRepository.save(attendance);
                attendanceList.add(attendance);
            }
        }
        // kiểm tra ngày hôm đó có phải là ngày cuối tuần hay không
        else if (dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY) {
            List<User> userList = userRepository.findAll();
            // kiểm nếu ngày cuối tuần đó không có log chấm công
            for (User u : userList) {
                if (logCheckInOutRepository.findByUserAndDateCheck(u.getId(), date).size() == 0) {
                    Attendance attendance = new Attendance(u, date);
                    attendance.setSigns(new Sign(ESign.NT));
                    attendanceRepository.save(attendance);
                    attendanceList.add(attendance);
                }
            }
        }
        // trường hợp không phải là ngày cuối tuần và ngày lễ
        else {
            List<Long> listUserId = logCheckInOutRepository.findDistinctUserIdByDateCheck(date);
            WorkingTime morningShift = workingTimeRepository.findByWorkingTimeName(EWorkingTime.MORNING_SHIFT).orElseThrow();
            WorkingTime afternoonShift = workingTimeRepository.findByWorkingTimeName(EWorkingTime.AFTERNOON_SHIFT).orElseThrow();
            for (Long i : listUserId) {
                User user = userRepository.findById(i).orElseThrow();
                List<LogCheckInOut> checkInOutRecords = logCheckInOutRepository.findByUserAndDateCheck(i, date);
                if (checkInOutRecords.size() > 0) {
                    // lưu vào attendance
                    LocalTime earliestCheckIn = checkInOutRecords.get(0).getTimeCheck();
                    LocalTime latestCheckOut = checkInOutRecords.get(checkInOutRecords.size() - 1).getTimeCheck();
                    Attendance attendance = new Attendance(user, date);
                    // set timein timeout
                    attendance.setTimeIn(earliestCheckIn);
                    attendance.setTimeOut(latestCheckOut);
                    // set RegularHour
                    // nếu làm cả ngày RegularHour phải trừ thời gian nghỉ trưa
                    if (attendance.getTimeIn().isBefore(morningShift.getEndTime()) && attendance.getTimeOut().isAfter(afternoonShift.getStartTime())) {

                        LocalTime startTime = attendance.getTimeIn();
                        LocalTime endTime = attendance.getTimeOut();
                        // nếu checkin trước gờ bắt đầu ca sáng thì giờ làm được tính từ giờ bắt đầu ca sáng
                        if (attendance.getTimeIn().isBefore(morningShift.getStartTime())) {
                            startTime = morningShift.getStartTime();
                        }
                        // nếu checkout sau giờ kết thúc ca chiều thì giờ làm được tính đến giờ kết thúc ca chiêều
                        if (attendance.getTimeOut().isAfter(afternoonShift.getEndTime())) {
                            endTime = afternoonShift.getEndTime();
                        }
                        LocalTime morning = morningShift.getEndTime()
                                .minusHours(startTime.getHour())
                                .minusMinutes(startTime.getMinute())
                                .minusSeconds(startTime.getSecond());
                        LocalTime afternoon = endTime
                                .minusHours(afternoonShift.getStartTime().getHour())
                                .minusMinutes(afternoonShift.getStartTime().getMinute())
                                .minusSeconds(afternoonShift.getStartTime().getSecond());
                        LocalTime regularHour = morning.plusHours(afternoon.getHour())
                                .plusMinutes(afternoon.getMinute())
                                .plusSeconds(afternoon.getSecond());
                        attendance.setRegularHour(regularHour);
                        attendance.setSigns(new Sign(ESign.H));
                    }
                    // nếu làm nửa ngày thì RegularHour không phải trừ thời gian nghỉ trưa
                    if (
                            (attendance.getTimeIn().isAfter(morningShift.getEndTime()) && attendance.getTimeOut().isAfter(afternoonShift.getStartTime())) ||
                                    (attendance.getTimeIn().isBefore(morningShift.getEndTime()) && attendance.getTimeOut().isBefore(afternoonShift.getStartTime()))
                    ) {
                        LocalTime startTime = attendance.getTimeIn();
                        LocalTime endTime = attendance.getTimeOut();
                        // nếu checkin sớm thì thời gian đi làm được tính từ thời gian bắt đầu ca
                        if (startTime.isBefore(morningShift.getStartTime())) {
                            startTime = morningShift.getStartTime();
                        } else if (startTime.isBefore(afternoonShift.getStartTime())) {
                            startTime = afternoonShift.getStartTime();
                        }
                        // nếu checkout muộn thì thời gian đi làm được tính đến thời gian kết thúc quy định
                        if (endTime.isAfter(morningShift.getEndTime())) {
                            endTime = morningShift.getEndTime();
                        } else if (endTime.isAfter(afternoonShift.getEndTime())) {
                            endTime = afternoonShift.getEndTime();
                        }
                        LocalTime regularHour = endTime
                                .minusHours(startTime.getHour())
                                .minusMinutes(startTime.getMinute())
                                .minusSeconds(startTime.getSecond());
                        attendance.setRegularHour(regularHour);
                        // set sign
                        if(attendance.getTimeIn().isBefore(morningShift.getEndTime())){
                            attendance.setSigns(new Sign(ESign.H_KL));
                        } else if (attendance.getTimeIn().isAfter(morningShift.getEndTime())) {
                            attendance.setSigns(new Sign(ESign.KL_H));
                        }
                    }
                    // set totalWork
                    if (attendance.getOverTime() == null) {
                        attendance.setTotalWork(attendance.getRegularHour());
                    } else {
                        attendance.setTotalWork(attendance.getRegularHour()
                                .plusHours(attendance.getOverTime().getHour())
                                .plusMinutes(attendance.getOverTime().getMinute())
                                .plusSeconds(attendance.getOverTime().getSecond()));
                    }
                    // save attendance
                    attendanceRepository.save(attendance);
                    attendanceList.add(attendance);
                    // lưu vào LogInLateOutEarly
                    if (attendance.getTimeIn().isAfter(morningShift.getStartTime())) {
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
                    if (attendance.getTimeOut().isBefore(afternoonShift.getEndTime())) {
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

    @Override
    public Page<AttendanceStatistics> getAttendanceStatisticsOnMonth(int month, int year, Pageable pageable) {
        List<AttendanceStatistics> attendanceStatisticsList = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for (User u : userList) {
            int workingDay = 0;
            int salaryDay = 0;
            List<Attendance> attendanceList = attendanceRepository.findAttendancesForUserInMonth(u.getId(), year, month);
            for (Attendance a : attendanceList) {
                if (a.getSigns().getName().equals(ESign.H)) {
                    workingDay += 1;
                    salaryDay += 1;
                } else if (a.getSigns().getName().equals(ESign.L) || a.getSigns().getName().equals(ESign.CĐ) ||
                        a.getSigns().getName().equals(ESign.TC) || a.getSigns().getName().equals(ESign.P)) {
                    salaryDay += 1;
                } else if (a.getSigns().getName().equals(ESign.P_KL) || a.getSigns().getName().equals(ESign.KL_P)) {
                    salaryDay += 0.5;
                } else if (a.getSigns().getName().equals(ESign.P_H) || a.getSigns().getName().equals(ESign.H_P)) {
                    workingDay += 0.5;
                    salaryDay += 1;
                } else if (a.getSigns().getName().equals(ESign.H_KL) || a.getSigns().getName().equals(ESign.KL_H)) {
                    workingDay += 0.5;
                    salaryDay += 0.5;
                }
            }
            AttendanceStatistics attendanceStatistics = new AttendanceStatistics(u, attendanceList, workingDay, salaryDay);
            attendanceStatisticsList.add(attendanceStatistics);

        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), attendanceStatisticsList.size());
        List<AttendanceStatistics> subList = attendanceStatisticsList.subList(start, end);
        Page<AttendanceStatistics> result = new PageImpl<>(subList, pageable, attendanceStatisticsList.size());
        return result;
    }

    public boolean hasRoleAdmin(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRoleMod(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            if ("ROLE_MODERATOR".equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Page<AttendanceResponse> getList(String departmentId, String fromDate, String toDate, Pageable pageable) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String did = departmentId == null || departmentId.toString() == "" ? "" : departmentId;
        String from = fromDate == null || fromDate.equals("") ? null : fromDate;
        String to = toDate == null || toDate.equals("") ? null : toDate;

        Page<Attendance> list = null;

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        boolean isAdmin = hasRoleAdmin(authorities);
        boolean isMod = hasRoleMod(authorities);
        if (isAdmin) {
            list = attendanceRepository.getList(
                    did
                    , null
                    , from
                    , to
                    , pageable);
        } else if (isMod) {
            list = attendanceRepository.getList(
                    userService.findByUsernameOrEmail(userDetails.getUsername()).get().getDepartment().getId().toString()
                    , null
                    , from
                    , to
                    , pageable);
        } else {
            list = attendanceRepository.getList(null, userDetails.getId().toString(), from, to, pageable);
        }
        Page<AttendanceResponse> result = list.map(new Function<Attendance, AttendanceResponse>() {
            @Override
            public AttendanceResponse apply(Attendance entity) {

                AttendanceResponse dto = new AttendanceResponse();
                // Conversion logic
                dto.setId(entity.getId());
                dto.setDepartment(entity.getUser().getDepartment().getName());
                dto.setEmployeeCode(entity.getUser().getUserCode());
                dto.setEmployeeName(entity.getUser().getFullName());
                dto.setDateLog(entity.getDateLog());
                dto.setTimeIn(entity.getTimeIn());
                dto.setTimeOut(entity.getTimeOut());
                return dto;
            }
        });
        return result;
    }
}
