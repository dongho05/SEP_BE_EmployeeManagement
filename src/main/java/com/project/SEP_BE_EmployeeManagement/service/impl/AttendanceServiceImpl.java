package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.request.attendance.EditAttendance;
import com.project.SEP_BE_EmployeeManagement.dto.response.MessageResponse;
import com.project.SEP_BE_EmployeeManagement.dto.response.attendance.AttendanceStatistics;
import com.project.SEP_BE_EmployeeManagement.dto.response.attendance.AttendanceResponse;
import com.project.SEP_BE_EmployeeManagement.dto.response.exception.UpdateNullException;
import com.project.SEP_BE_EmployeeManagement.model.*;
import com.project.SEP_BE_EmployeeManagement.repository.*;
import com.project.SEP_BE_EmployeeManagement.security.jwt.UserDetailsImpl;
import com.project.SEP_BE_EmployeeManagement.service.AttendanceService;
import com.project.SEP_BE_EmployeeManagement.service.HolidayService;
import com.project.SEP_BE_EmployeeManagement.service.LogAttendanceHistoryService;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private SignRepository signRepository;

    @Autowired
    private NoteCatergoryRepository noteCatergoryRepository;

    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private UserService userService;

    @Autowired
    private LogAttendanceHistoryService logAttendanceHistoryService;

    @Override
    public void startEditing(Long entityId, Long userId) {
        Attendance attendance = attendanceRepository.findById(entityId).orElse(null);

        if (attendance != null && attendance.getEditingUser() == null) {
            // Bắt đầu chỉnh sửa bản ghi
            User user = new User();
            user.setId(userId);
            attendance.setEditingUser(user);

            attendanceRepository.save(attendance);
            // Thực hiện các thay đổi trong bản ghi
        } else {
            // Bản ghi đã được chỉnh sửa bởi người khác
            throw new RuntimeException("Record is already being edited by another user");
        }
    }

    @Override
    public void finishEditing(Long entityId) {
        Attendance attendance = attendanceRepository.findById(entityId).orElse(null);

        if (attendance != null && attendance.getEditingUser() != null) {
            // Kết thúc chỉnh sửa bản ghi
            attendance.setEditingUser(null);

            attendanceRepository.save(attendance);
        } else {
            // Bản ghi không được chỉnh sửa
            throw new RuntimeException("Record is not being edited");
        }
    }

    @Override
    public List<Attendance> getForCalendar(String code, Integer year) {
        List<Attendance> attendances = attendanceRepository.findByUserCodeAndMonthAndYear(code,year);
        return attendances;
    }

//    @Override
//    @Scheduled(cron = "0 0 22 * * ?") // process attendance vào 22h hàng ngày
//    public List<Attendance> processAttendanceForUserOnDate() throws NotFoundException {
    @Override
    public List<Attendance> processAttendanceForUserOnDate(Integer day, Integer month, Integer year) throws NotFoundException {

        LocalDate date = LocalDate.now();
        if(day!=null && month!=null && year!=null){
            date = LocalDate.of(year, month, day);
        }
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
    public Page<AttendanceResponse> getList(String departmentId, String searchInput, String fromDate, String toDate, Pageable pageable) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String did = departmentId == null || departmentId.toString() == "" ? "" : departmentId;
        String from = fromDate == null || fromDate.equals("") ? null : fromDate;
        String to = toDate == null || toDate.equals("") ? null : toDate;
        String search = searchInput == null || searchInput.equals("") ? null : searchInput;


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
        if(search == null){
            return result;

        }
        List<AttendanceResponse> listFilter = result.stream()
                .filter(dto->dto.getEmployeeCode().contains(search) || dto.getEmployeeName().contains(search))
                .collect(Collectors.toList());

        return new PageImpl<>(listFilter,pageable,listFilter.size());
    }

    @Override
    public Page<AttendanceResponse> getListByUserId(String fromDate, String toDate, Pageable pageable) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String from = fromDate == null || fromDate.equals("") ? null : fromDate;
        String to = toDate == null || toDate.equals("") ? null : toDate;

        Page<Attendance> list = attendanceRepository.getList(null, userDetails.getId().toString(), from, to, pageable);
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

    @Override
    public Attendance getByAttendanceId(Long id) {
        Attendance obj = attendanceRepository.findById(id).get();
        return obj;
    }

    @Override
    public Attendance updateSigns(ESign signId, Long attendanceId, String reason) {
        Attendance obj = attendanceRepository.findById(attendanceId).get();
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        LogAttendanceHistory log = new LogAttendanceHistory();
        log.setOldSign(obj.getSigns());

        obj.setSigns(new Sign(signId));
        User user = userRepository.findById(userDetails.getId()).get();

        log.setAttendance(attendanceRepository.findById(attendanceId).get());
        log.setNewSign(new Sign(signId));
        log.setReason(reason);
        log.setUpdatedBy(user.getFullName() + "-" + user.getPosition().getPositionName());
        log.setUpdatedDate(dateFormat.format(date));

        logAttendanceHistoryService.save(log);

        return obj;
    }

    @Override
    public MessageResponse updateAttendance(EditAttendance[] editAttendances) {
        if(editAttendances.length==0){
            throw  new UpdateNullException("Chưa có chỉnh sửa nào");
        }
        for(EditAttendance editAttendances1 : editAttendances){
            String[] time = editAttendances1.getDate().split("-");
            LocalDate date = LocalDate.of(Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
            System.out.println(date.toString());
            Attendance attendance = new Attendance() ;
            if(attendanceRepository.findByUserCodeAndDate(editAttendances1.getCode(), date)!=null){
                attendance= attendanceRepository.findByUserCodeAndDate(editAttendances1.getCode(), date);
                Sign currentSign = attendance.getSigns();
                if(editAttendances1.getSign()==null)
                    attendance.setSigns(null);
                else
                    attendance.setSigns(signRepository.findByName(ESign.valueOf(editAttendances1.getSign())));
                if(editAttendances1.getSign() == attendance.getSigns().getName().toString())
                    continue;
                else {
                    Set<NoteLog> noteCatergorySet = attendance.getNoteLogSet();
                    if (noteCatergorySet == null)
                        noteCatergorySet = new HashSet<>();
                    NoteLog noteLog = new NoteLog();
                    noteLog.setAttendance(attendance);
                    noteLog.setNoteCategory(noteCatergoryRepository.findByName(ENoteCatergory.E_EDIT));
                    noteLog.setContent(editAttendances1.getReason());
                    noteLog.setAdminEdit(userRepository.findByUserCode(editAttendances1.getCodeAdminEdit()));
                    noteLog.setLastSign(currentSign);
                    noteLog.setCreateDate(LocalDateTime.now());
                    noteLog.setSignChange(signRepository.findByName(ESign.valueOf(editAttendances1.getSign())));
                    noteCatergorySet.add(noteLog);
                    attendance.setNoteLogSet(noteCatergorySet);
                    attendance.setEditReason(editAttendances1.getReason());
                }
            }
            else {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                attendance.setUser(userRepository.findByUserCode(editAttendances1.getCode()));
//                attendance.setDateLog(LocalDate.parse(editAttendances1.getDate(),sdf));
                attendance.setDateLog(date);
                if(editAttendances1.getSign()==null)
                    attendance.setSigns(null);
                else
                    attendance.setSigns(signRepository.findByName(ESign.valueOf(editAttendances1.getSign())));
                if(editAttendances1.getSign()==null)
                    continue;
                Set<NoteLog> noteCatergorySet = attendance.getNoteLogSet();
                if(noteCatergorySet==null)
                    noteCatergorySet = new HashSet<>();
                NoteLog noteLog = new NoteLog();
                noteLog.setAttendance(attendance);
                noteLog.setNoteCategory(noteCatergoryRepository.findByName(ENoteCatergory.E_EDIT));
                noteLog.setContent(editAttendances1.getReason());
                noteLog.setAdminEdit(userRepository.findByUserCode(editAttendances1.getCodeAdminEdit()));
                noteLog.setLastSign(null);
                noteLog.setCreateDate(LocalDateTime.now());
                noteLog.setSignChange(signRepository.findByName(ESign.valueOf(editAttendances1.getSign())));
                noteCatergorySet.add(noteLog);
                attendance.setNoteLogSet(noteCatergorySet);
                attendance.setEditReason(editAttendances1.getReason());
            }

            attendanceRepository.save(attendance);
//            finishEditing(attendance.getId());
        }
        return new MessageResponse("Successfully!");
    }
}
