package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateReqRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.request.RequestResponse;
import com.project.SEP_BE_EmployeeManagement.model.*;
import com.project.SEP_BE_EmployeeManagement.repository.*;
import com.project.SEP_BE_EmployeeManagement.security.jwt.UserDetailsImpl;
import com.project.SEP_BE_EmployeeManagement.service.DepartmentService;
import com.project.SEP_BE_EmployeeManagement.service.RequestService;
import com.project.SEP_BE_EmployeeManagement.service.RequestTypeService;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Function;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserService userService;

    @Autowired
    RequestTypeService requestTypeService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @Autowired
    private NoteCatergoryRepository noteCatergoryRepository;

    @Autowired
    private SignRepository signRepository;

    @Override
    public Request createRequest(CreateReqRequest request) {

//        parse localDate to date
        LocalDate localDate = LocalDate.now();
        Double numberOfDays = 0.0;
        Request obj = new Request();

        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Double dayOff = userService.getDayOffByUserId(userDetails.getId());


        if (request.getRequestTypeId() == 1) {
            LocalDateTime startTime = LocalDateTime.of(request.getStartDate(), request.getStartTime());
            LocalDateTime endTime = LocalDateTime.of(request.getEndDate(), request.getEndTime());

            Duration duration = Duration.between(startTime, endTime);
            long days = duration.toDays();

            if(checkFullTime(request.getStartTime(),request.getEndTime()) == true){
                numberOfDays = days + 1.0;
            }
            if(checkPartTime(request.getStartTime(),request.getEndTime()) == true){
                numberOfDays = 0.5;
            }

            if(dayOff < 0){
                throw new RuntimeException("Bạn đã hết ngày nghỉ.");
            }
            if (numberOfDays <= dayOff) {
                User user = userRepository.findById(userDetails.getId()).orElseThrow();
                user.setDayoff(dayOff - numberOfDays);
                userRepository.save(user);
            }else{
                throw new RuntimeException("Ngày nghỉ phép hiện có không đủ để tạo đơn xin nghỉ.");
            }
        }

        obj.setRequestContent(request.getRequestContent());
        obj.setRequestTitle(request.getRequestTitle());
        obj.setEndDate(request.getEndDate());
        obj.setStartDate(request.getStartDate());
        obj.setStartTime(request.getStartTime());
        obj.setEndTime(request.getEndTime());
        obj.setStatus(1);
        obj.setRequestType(requestTypeService.findById(request.getRequestTypeId()));
        obj.setUser(userRepository.findById(userDetails.getId()).get());

        requestRepository.save(obj);
        return obj;
    }

    public boolean checkFullTime(LocalTime startTime, LocalTime endTime){
        WorkingTime workingTime = workingTimeRepository.findByWorkingTimeName(EWorkingTime.FULLTIME).orElseThrow();
        if(startTime.compareTo(workingTime.getStartTime()) == 0 && endTime.compareTo(workingTime.getEndTime()) ==0){
            return true;
        }
        return false;
    }

    public boolean checkPartTime(LocalTime startTime, LocalTime endTime){
        WorkingTime morningTime = workingTimeRepository.findByWorkingTimeName(EWorkingTime.MORNING_SHIFT).orElseThrow();
        WorkingTime afternoonTime = workingTimeRepository.findByWorkingTimeName(EWorkingTime.AFTERNOON_SHIFT).orElseThrow();
        if((startTime.compareTo(morningTime.getStartTime()) == 0 && endTime.compareTo(morningTime.getEndTime()) == 0) ||
                (startTime.compareTo(afternoonTime.getStartTime()) == 0 && endTime.compareTo(afternoonTime.getEndTime()) == 0)
        ){
            return true;
        }
        return false;
    }

    @Override
    public void updateRequest(long id , int statusRequest) {
        LocalDate localDate = LocalDate.now();

        Double numberOfDays = 0.0;

        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Double dayOff = userService.getDayOffByUserId(userDetails.getId());


        Request obj = requestRepository.findById(id);
        if (obj == null) {
            throw new RuntimeException("Không tìm thấy yêu cầu");
        }

        if (obj != null) {
            obj.setStatus(statusRequest);
            obj.setAcceptBy(userDetails.getId());
            obj.setAcceptAt(localDate);
            if (obj.getRequestType().getId() == 1) {
                if (statusRequest == 4) {
                    LocalDateTime startTime = LocalDateTime.of(obj.getStartDate(), obj.getStartTime());
                    LocalDateTime endTime = LocalDateTime.of(obj.getEndDate(), obj.getEndTime());

                    Duration duration = Duration.between(startTime, endTime);
                    long days = duration.toDays();
                    if(checkFullTime(obj.getStartTime(),obj.getEndTime()) == true){
                        numberOfDays = days + 1.0 ;
                    }
                    if(checkPartTime(obj.getStartTime(),obj.getEndTime()) == true){
                        numberOfDays = 0.5;
                    }

                    User user = userRepository.findById(userDetails.getId()).get();
                    user.setDayoff(dayOff + numberOfDays);
                    userRepository.save(user);
                }
            }
            requestRepository.save(obj);
        }
    }

    @Override
    public RequestResponse findById(long id) {
        Request entity = requestRepository.findById(id);

        String handlerName = entity.getAcceptBy() == 0 ? "Chưa xử lý" : userRepository.findById(entity.getAcceptBy()).get().getFullName();
        String handlerPosition = entity.getAcceptBy() == 0 ? "Chưa xử lý" : userRepository.findById(entity.getAcceptBy()).get().getPosition().getPositionName();

        RequestResponse dto = new RequestResponse();
        dto.setId(entity.getId());
        dto.setRequestContent(entity.getRequestContent());
        dto.setRequestTitle(entity.getRequestTitle());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setEndDate(entity.getEndDate());
        dto.setEndTime(entity.getEndTime());
        dto.setRequestTypeId(entity.getRequestType().getId());
        dto.setStartDate(entity.getStartDate());
        dto.setStartTime(entity.getStartTime());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedDate(entity.getUpdatedDate());
        dto.setUserId(entity.getUser().getId());
        dto.setStatus(entity.getStatus());
        dto.setDepartment(entity.getUser().getDepartment());
        dto.setUser(entity.getUser());
        dto.setRequestType(entity.getRequestType());
        dto.setNumberOfDays(DAYS.between(entity.getStartDate(), entity.getEndDate()) + 1);
        dto.setNote(entity.getNote());
        dto.setHandlerName(handlerName);
        dto.setHandlerPosition(handlerPosition);

        return dto;
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
    public Page<RequestResponse> getList(String searchInput, String departmentId, String statusReq, String fromDate, String toDate,String requestType, Pageable pageable) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String search = searchInput == null || searchInput.toString() == "" ? "" : searchInput;
        String did = departmentId == null || departmentId.toString() == "" ? "" : departmentId;
        String from = fromDate == null || fromDate.equals("") ? null : fromDate;
        String requestTypeInput = requestType == null || requestType.equals("") ? null : requestType;
        String to = toDate == null || toDate.equals("") ? null : toDate;
        String status = statusReq == null || statusReq.equals("") ? "" : statusReq;

        Page<Request> list = null;

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        boolean isAdmin = hasRoleAdmin(authorities);
        boolean isMod = hasRoleMod(authorities);
        if (isAdmin) {
            list = requestRepository.getList(search
                    , did
                    , status
                    , null
                    , from
                    , to
                    ,requestTypeInput
                    , pageable);
        } else if (isMod) {
            list = requestRepository.getList(search
                    , userService.findByUsernameOrEmail(userDetails.getUsername()).get().getDepartment().getId().toString()
                    , status
                    , null
                    , from
                    , to
                    ,requestTypeInput
                    , pageable);
        } else {
            list = requestRepository.getList(search, null, status, userDetails.getId(), from, to,requestTypeInput, pageable);
        }

        Page<RequestResponse> result = list.map(new Function<Request, RequestResponse>() {
            @Override
            public RequestResponse apply(Request entity) {

                RequestResponse dto = new RequestResponse();
                // Conversion logic
                dto.setId(entity.getId());
                dto.setRequestContent(entity.getRequestContent());
                dto.setRequestTitle(entity.getRequestTitle());
                dto.setCreatedBy(entity.getCreatedBy());
                dto.setCreatedDate(entity.getCreatedDate());
                dto.setEndDate(entity.getEndDate());
                dto.setEndTime(entity.getEndTime());
                dto.setRequestTypeId(entity.getRequestType().getId());
                dto.setStartDate(entity.getStartDate());
                dto.setStartTime(entity.getStartTime());
                dto.setUpdatedBy(entity.getUpdatedBy());
                dto.setUpdatedDate(entity.getUpdatedDate());
                dto.setUserId(entity.getUser().getId());
                dto.setStatus(entity.getStatus());
                dto.setDepartmentId(Math.toIntExact(entity.getUser().getDepartment().getId()));
                dto.setDepartment(entity.getUser().getDepartment());
                dto.setUser(entity.getUser());
                dto.setNote(entity.getNote());
                dto.setRequestType(entity.getRequestType());

                return dto;
            }
        });
        return result;
    }

    @Override
    public Page<RequestResponse> getListByUserId(String searchInput, String statusReq, String fromDate, String toDate,String requestType, Pageable pageable) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String search = searchInput == null || searchInput.toString() == "" ? "" : searchInput;
        String from = fromDate == null || fromDate.equals("") ? null : fromDate;
        String requestTypeInput = requestType == null || requestType.equals("") ? null : requestType;
        String to = toDate == null || toDate.equals("") ? null : toDate;
        String status = statusReq == null || statusReq.equals("") ? "" : statusReq;

        Page<Request> list = requestRepository.getList(search, null, status, userDetails.getId(), from, to,requestTypeInput, pageable);

        Page<RequestResponse> result = list.map(new Function<Request, RequestResponse>() {
            @Override
            public RequestResponse apply(Request entity) {

                RequestResponse dto = new RequestResponse();
                // Conversion logic
                dto.setId(entity.getId());
                dto.setRequestContent(entity.getRequestContent());
                dto.setRequestTitle(entity.getRequestTitle());
                dto.setCreatedBy(entity.getCreatedBy());
                dto.setCreatedDate(entity.getCreatedDate());
                dto.setEndDate(entity.getEndDate());
                dto.setEndTime(entity.getEndTime());
                dto.setRequestTypeId(entity.getRequestType().getId());
                dto.setStartDate(entity.getStartDate());
                dto.setStartTime(entity.getStartTime());
                dto.setUpdatedBy(entity.getUpdatedBy());
                dto.setUpdatedDate(entity.getUpdatedDate());
                dto.setUserId(entity.getUser().getId());
                dto.setStatus(entity.getStatus());
                dto.setDepartmentId(Math.toIntExact(entity.getUser().getDepartment().getId()));
                dto.setDepartment(entity.getUser().getDepartment());
                dto.setUser(entity.getUser());
                dto.setNote(entity.getNote());
                dto.setRequestType(entity.getRequestType());
                return dto;
            }
        });
        return result;
    }

    //    1: Đang xử lý, 2: Chấp nhận, 3: Từ chối
    @Override
    public void updateStatusRequest(long requestId, int statusRequest, String note) {
        LocalDate localDate = LocalDate.now();

        Double numberOfDays = 0.0;

        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Double dayOff = userService.getDayOffByUserId(userDetails.getId());

        Request obj = requestRepository.findById(requestId);
        if (obj != null) {
            obj.setStatus(statusRequest);
            obj.setAcceptBy(userDetails.getId());
            obj.setAcceptAt(localDate);
            obj.setNote(note);
            if (obj.getRequestType().getId() == 1) {
                if (statusRequest == 3) {
                    LocalDateTime startTime = LocalDateTime.of(obj.getStartDate(), obj.getStartTime());
                    LocalDateTime endTime = LocalDateTime.of(obj.getEndDate(), obj.getEndTime());

                    Duration duration = Duration.between(startTime, endTime);
                    long days = duration.toDays();
                    if(checkFullTime(obj.getStartTime(),obj.getEndTime()) == true){
                        numberOfDays = days + 1.0 ;
                    }
                    if(checkPartTime(obj.getStartTime(),obj.getEndTime()) == true){
                        numberOfDays = 0.5;
                    }

                    User user = userRepository.findById(userDetails.getId()).get();
                    user.setDayoff(dayOff + numberOfDays);
                    userRepository.save(user);
                }
            }
            requestRepository.save(obj);
        }
    }

    //    @Override
//    @Scheduled(cron = "0 0 22 * * ?")// chạy vào 20h mỗi ngày
//    public List<Request> processRequestOnMonth() {
    @Override
    public List<Request> processRequestOnMonth(Integer day, Integer month, Integer year) {
        LocalDate date = LocalDate.now();
        if (day != null && month != null && year != null) {
            date = LocalDate.of(year, month, day);
        }
//        LocalDate date = LocalDate.now();
        List<Request> requestList = new ArrayList<>();
        // lấy danh sách tất cả các request chưa được duyệt từ đầu tháng tới ngày hiện tại
        requestList = requestRepository.findRequestsAcceptedInCurrentMonth(date);
        if (requestList.size() > 0) {
            // duyệt danh sách request
            for (Request i : requestList) {
                // list các attendance liên quan đến request
                List<Attendance> attendanceList = new ArrayList<>();
                // duyệt qua các ngày trong request
                LocalDate startDate = i.getStartDate();
                LocalDate endDate = i.getEndDate();
                LocalDate currentDate = startDate;
                while (!currentDate.isAfter(endDate)) {
                    Attendance attendance = attendanceRepository.findAttendanceByUserAndDate(i.getUser().getId(), currentDate);
                    if (attendance == null) {
                        attendance = new Attendance(i.getUser(), currentDate);
                    }
                    attendanceList.add(attendance);
                    // Di chuyển đến ngày tiếp theo
                    currentDate = currentDate.plusDays(1);
                }
                WorkingTime morningShift = workingTimeRepository.findByWorkingTimeName(EWorkingTime.MORNING_SHIFT).orElseThrow();
                WorkingTime afternoonShift = workingTimeRepository.findByWorkingTimeName(EWorkingTime.AFTERNOON_SHIFT).orElseThrow();

                int checkRequestType = i.getRequestType().getId();
                switch (checkRequestType) {
                    case 1: // nghỉ có lương
                        // xin nghỉ buổi sáng: start không  sau giờ kết thúc buổi sáng và end không sau giờ bắt đầu buổi chiều
                        if (!i.getStartTime().isAfter(morningShift.getEndTime()) && !i.getEndTime().isAfter(afternoonShift.getStartTime())) {

                            // kiểm tra xem có log attendance hay không
                            for (Attendance a : attendanceList) {

                                // Buổi sáng có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        a.getTimeIn().isBefore(morningShift.getEndTime())) {
                                    // dừng duyệt request
                                    i.setCheck(true);
                                    requestRepository.save(i);
                                    break;
                                }

                                // lưu vào bảng note log
                                Set<NoteLog> noteCatergorySet = a.getNoteLogSet();
                                if (noteCatergorySet == null)
                                    noteCatergorySet = new HashSet<>();
                                NoteLog noteLog = new NoteLog();
                                noteLog.setAttendance(a);
                                noteLog.setNoteCategory(noteCatergoryRepository.findByName(ENoteCatergory.E_REQUEST));
                                noteLog.setContent(i.getRequestType().getRequestTypeName());
//                                    noteLog.setAdminEdit(userRepository.findByUserCode(editAttendances1.getCodeAdminEdit()));
                                if (a.getSigns() == null) {
                                    noteLog.setLastSign(null);
                                } else {
                                    noteLog.setLastSign(a.getSigns());
                                }
                                noteLog.setCreateDate(LocalDateTime.now());
//                                noteLog.setSignChange(new Sign(ESign.CĐ));
                                noteCatergorySet.add(noteLog);
                                noteLog.setApproversRequest(userRepository.findById(i.getAcceptBy()).orElseThrow());
                                a.setNoteLogSet(noteCatergorySet);
                                a.setEditReason(i.getRequestType().getRequestTypeName());

                                // lấy kis tự chấm công cũ
                                String[] signs = {"", ""};
                                if (a.getSigns() != null) {
                                    String[] s = a.getSigns().getName().toString().split("_");
                                    for (int j = 0; j < Math.min(s.length, signs.length); j++) {
                                        signs[j] = s[j];
                                    }
                                }

                                // Buổi chiều có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        a.getTimeIn().isAfter(morningShift.getEndTime()) && a.getTimeOut().isAfter(afternoonShift.getStartTime())) {
                                    noteLog.setSignChange(signRepository.findByName(ESign.P_H));
                                    a.setSigns(signRepository.findByName(ESign.P_H));
//                                    LocalTime deltaTime = morningShift.getEndTime()
//                                            .minusHours(morningShift.getStartTime().getHour())
//                                            .minusMinutes(morningShift.getStartTime().getMinute())
//                                            .minusSeconds(morningShift.getStartTime().getSecond());
//                                    LocalTime regularHour = a.getRegularHour().plusHours(deltaTime.getHour()).plusMinutes(deltaTime.getMinute()).plusSeconds(deltaTime.getSecond());
//                                    a.setRegularHour(regularHour);
                                }

                                // không chấm công
                                if (a.getTimeIn() == null && a.getTimeOut() == null) {
                                    if (signs[1].equals("P")) {
                                        noteLog.setSignChange(signRepository.findByName(ESign.P));
                                        a.setSigns(signRepository.findByName(ESign.P));
//                                        LocalTime morning = morningShift.getEndTime()
//                                                .minusHours(morningShift.getStartTime().getHour())
//                                                .minusMinutes(morningShift.getStartTime().getMinute())
//                                                .minusSeconds(morningShift.getStartTime().getSecond());
//                                        LocalTime afternoon = afternoonShift.getEndTime()
//                                                .minusHours(afternoonShift.getStartTime().getHour())
//                                                .minusMinutes(afternoonShift.getStartTime().getMinute())
//                                                .minusSeconds(afternoonShift.getStartTime().getSecond());
//                                        LocalTime regularHour = morning.plusHours(afternoon.getHour())
//                                                .plusMinutes(afternoon.getMinute())
//                                                .plusSeconds(afternoon.getSecond());
//                                        a.setRegularHour(regularHour);
                                    } else {
                                        noteLog.setSignChange(signRepository.findByName(ESign.P_KL));
                                        a.setSigns(signRepository.findByName(ESign.P_KL));
//                                        LocalTime regularHour = morningShift.getEndTime().minusHours(morningShift.getStartTime().getHour())
//                                                .minusMinutes(morningShift.getStartTime().getMinute())
//                                                .minusSeconds(morningShift.getStartTime().getSecond());
//                                        a.setRegularHour(regularHour);
                                    }
                                }
                            }
                        }

                        // xin nghỉ buổi chiều: start sau giờ kết thúc buooir sáng và end không trước giờ bắt đầu buổi chiều
                        if (i.getStartTime().isAfter(morningShift.getEndTime()) && !i.getEndTime().isBefore(afternoonShift.getStartTime())) {
                            // kiểm tra xem có log attendance hay không
                            for (Attendance a : attendanceList) {

                                // buổi chiều có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        a.getTimeOut().isBefore(afternoonShift.getStartTime())) {
                                    // dừng duyệt request
                                    i.setCheck(true);
                                    requestRepository.save(i);
                                    break;
                                }

                                // lưu vào bảng note log
                                Set<NoteLog> noteCatergorySet = a.getNoteLogSet();
                                if (noteCatergorySet == null)
                                    noteCatergorySet = new HashSet<>();
                                NoteLog noteLog = new NoteLog();
                                noteLog.setAttendance(a);
                                noteLog.setNoteCategory(noteCatergoryRepository.findByName(ENoteCatergory.E_REQUEST));
                                noteLog.setContent(i.getRequestType().getRequestTypeName());
//                                    noteLog.setAdminEdit(userRepository.findByUserCode(editAttendances1.getCodeAdminEdit()));
                                if (a.getSigns() == null) {
                                    noteLog.setLastSign(null);
                                } else {
                                    noteLog.setLastSign(a.getSigns());
                                }
                                noteLog.setCreateDate(LocalDateTime.now());
//                                noteLog.setSignChange(new Sign(ESign.CĐ));
                                noteCatergorySet.add(noteLog);
                                noteLog.setApproversRequest(userRepository.findById(i.getAcceptBy()).orElseThrow());
                                a.setNoteLogSet(noteCatergorySet);
                                a.setEditReason(i.getRequestType().getRequestTypeName());

                                // lấy kis tự chấm công cũ
                                String[] signs = {"", ""};
                                if (a.getSigns() != null) {
                                    String[] s = a.getSigns().getName().toString().split("_");
                                    for (int j = 0; j < Math.min(s.length, signs.length); j++) {
                                        signs[j] = s[j].trim();
                                    }
                                }

                                // buổi sáng có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        a.getTimeIn().isBefore(morningShift.getEndTime()) && a.getTimeOut().isBefore(afternoonShift.getStartTime())) {
                                    noteLog.setSignChange(signRepository.findByName(ESign.H_P));
                                    a.setSigns(signRepository.findByName(ESign.H_P));
//                                    LocalTime deltaTime = afternoonShift.getEndTime()
//                                            .minusHours(afternoonShift.getStartTime().getHour())
//                                            .minusMinutes(afternoonShift.getStartTime().getMinute())
//                                            .minusSeconds(afternoonShift.getStartTime().getSecond());
//                                    LocalTime regularHour = a.getRegularHour().plusHours(deltaTime.getHour()).plusMinutes(deltaTime.getMinute()).plusSeconds(deltaTime.getSecond());
//                                    a.setRegularHour(regularHour);
                                }

                                // không chấm công
                                if (a.getTimeIn() == null && a.getTimeOut() == null) {
                                    if (signs[0].equals("P")) {
                                        noteLog.setSignChange(signRepository.findByName(ESign.P));
                                        a.setSigns(signRepository.findByName(ESign.P));
//                                        LocalTime morning = morningShift.getEndTime()
//                                                .minusHours(morningShift.getStartTime().getHour())
//                                                .minusMinutes(morningShift.getStartTime().getMinute())
//                                                .minusSeconds(morningShift.getStartTime().getSecond());
//                                        LocalTime afternoon = afternoonShift.getEndTime()
//                                                .minusHours(afternoonShift.getStartTime().getHour())
//                                                .minusMinutes(afternoonShift.getStartTime().getMinute())
//                                                .minusSeconds(afternoonShift.getStartTime().getSecond());
//                                        LocalTime regularHour = morning.plusHours(afternoon.getHour())
//                                                .plusMinutes(afternoon.getMinute())
//                                                .plusSeconds(afternoon.getSecond());
//                                        a.setRegularHour(regularHour);
                                    } else {
                                        noteLog.setSignChange(signRepository.findByName(ESign.KL_P));
                                        a.setSigns(signRepository.findByName(ESign.KL_P));
//                                        LocalTime regularHour = afternoonShift.getEndTime()
//                                                .minusHours(afternoonShift.getStartTime().getHour())
//                                                .minusMinutes(afternoonShift.getStartTime().getMinute())
//                                                .minusSeconds(afternoonShift.getStartTime().getSecond());
//                                        a.setRegularHour(regularHour);
                                    }
                                }
                            }
                        }

                        // xin nghỉ cả ngày: start không sau giờ kết thúc buổi sáng và end không trước giờ bắt đầu buổi chiều
                        if (!i.getStartTime().isAfter(morningShift.getEndTime()) && !i.getEndTime().isBefore(afternoonShift.getStartTime())) {
                            // kiểm tra xem có log attendance hay không
                            for (Attendance a : attendanceList) {
                                // cả ngày có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        !a.getTimeIn().isAfter(morningShift.getEndTime()) && !a.getTimeOut().isBefore(afternoonShift.getStartTime())) {
                                    // dừng duyệt request
                                    i.setCheck(true);
                                    requestRepository.save(i);
                                    break;
                                }

                                // lưu vào bảng note log
                                Set<NoteLog> noteCatergorySet = a.getNoteLogSet();
                                if (noteCatergorySet == null)
                                    noteCatergorySet = new HashSet<>();
                                NoteLog noteLog = new NoteLog();
                                noteLog.setAttendance(a);
                                noteLog.setNoteCategory(noteCatergoryRepository.findByName(ENoteCatergory.E_REQUEST));
                                noteLog.setContent(i.getRequestType().getRequestTypeName());
//                                    noteLog.setAdminEdit(userRepository.findByUserCode(editAttendances1.getCodeAdminEdit()));
                                if (a.getSigns() == null) {
                                    noteLog.setLastSign(null);
                                } else {
                                    noteLog.setLastSign(a.getSigns());
                                }
                                noteLog.setCreateDate(LocalDateTime.now());
//                                noteLog.setSignChange(new Sign(ESign.CĐ));
                                noteCatergorySet.add(noteLog);
                                noteLog.setApproversRequest(userRepository.findById(i.getAcceptBy()).orElseThrow());
                                a.setNoteLogSet(noteCatergorySet);
                                a.setEditReason(i.getRequestType().getRequestTypeName());

                                // buổi chiều có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        a.getTimeIn().isAfter(morningShift.getEndTime()) && a.getTimeOut().isAfter(afternoonShift.getStartTime())) {
                                    a.setSigns(signRepository.findByName(ESign.P_H));
                                    noteLog.setSignChange(signRepository.findByName(ESign.P_H));
//                                    LocalTime deltaTime = morningShift.getEndTime()
//                                            .minusHours(morningShift.getStartTime().getHour())
//                                            .minusMinutes(morningShift.getStartTime().getMinute())
//                                            .minusSeconds(morningShift.getStartTime().getSecond());
//                                    LocalTime regularHour = a.getRegularHour().plusHours(deltaTime.getHour()).plusMinutes(deltaTime.getMinute()).plusSeconds(deltaTime.getSecond());
//                                    a.setRegularHour(regularHour);
                                }

                                // buổi sáng có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        a.getTimeIn().isBefore(morningShift.getEndTime()) && a.getTimeOut().isBefore(afternoonShift.getStartTime())) {
                                    noteLog.setSignChange(signRepository.findByName(ESign.H_P));
                                    a.setSigns(signRepository.findByName(ESign.H_P));
//                                    LocalTime deltaTime = afternoonShift.getEndTime()
//                                            .minusHours(afternoonShift.getStartTime().getHour())
//                                            .minusMinutes(afternoonShift.getStartTime().getMinute())
//                                            .minusSeconds(afternoonShift.getStartTime().getSecond());
//                                    LocalTime regularHour = a.getRegularHour().plusHours(deltaTime.getHour()).plusMinutes(deltaTime.getMinute()).plusSeconds(deltaTime.getSecond());
//                                    a.setRegularHour(regularHour);
                                }

                                // lấy kis tự chấm công cũ
                                String[] signs = {"", ""};
                                if (a.getSigns() != null) {
                                    String[] s = a.getSigns().getName().toString().split("_");
                                    for (int j = 0; j < Math.min(s.length, signs.length); j++) {
                                        signs[j] = s[j].trim();
                                    }
                                }

                                // không chấm công
                                if (a.getTimeIn() == null && a.getTimeOut() == null) {
                                    // chưa xin nghỉ phép
                                    noteLog.setSignChange(signRepository.findByName(ESign.P));
                                    a.setSigns(signRepository.findByName(ESign.P));
//                                    LocalTime morning = morningShift.getEndTime()
//                                            .minusHours(morningShift.getStartTime().getHour())
//                                            .minusMinutes(morningShift.getStartTime().getMinute())
//                                            .minusSeconds(morningShift.getStartTime().getSecond());
//                                    LocalTime afternoon = afternoonShift.getEndTime()
//                                            .minusHours(afternoonShift.getStartTime().getHour())
//                                            .minusMinutes(afternoonShift.getStartTime().getMinute())
//                                            .minusSeconds(afternoonShift.getStartTime().getSecond());
//                                    LocalTime regularHour = morning.plusHours(afternoon.getHour())
//                                            .plusMinutes(afternoon.getMinute())
//                                            .plusSeconds(afternoon.getSecond());
//                                    a.setRegularHour(regularHour);
                                }
                            }
                        }
                        i.setCheck(true);
                        requestRepository.save(i);
                        break;

                    case 2: // nghỉ không lương

                        // xin nghỉ buổi sáng: start và end không sau giờ kết thúc buổi sáng
                        if (!i.getStartTime().isAfter(morningShift.getEndTime()) && !i.getEndTime().isAfter(afternoonShift.getStartTime())) {
                            // kiểm tra xem có log attendance hay không
                            for (Attendance a : attendanceList) {
                                // buổi sáng có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        !a.getTimeIn().isAfter(morningShift.getEndTime())) {
                                    i.setCheck(true);
                                    requestRepository.save(i);
                                    break;
                                }

                                // lưu vào bảng note log
                                Set<NoteLog> noteCatergorySet = a.getNoteLogSet();
                                if (noteCatergorySet == null)
                                    noteCatergorySet = new HashSet<>();
                                NoteLog noteLog = new NoteLog();
                                noteLog.setAttendance(a);
                                noteLog.setNoteCategory(noteCatergoryRepository.findByName(ENoteCatergory.E_REQUEST));
                                noteLog.setContent(i.getRequestType().getRequestTypeName());
//                                    noteLog.setAdminEdit(userRepository.findByUserCode(editAttendances1.getCodeAdminEdit()));
                                if (a.getSigns() == null) {
                                    noteLog.setLastSign(null);
                                } else {
                                    noteLog.setLastSign(a.getSigns());
                                }
                                noteLog.setCreateDate(LocalDateTime.now());
//                                noteLog.setSignChange(new Sign(ESign.CĐ));
                                noteCatergorySet.add(noteLog);
                                noteLog.setApproversRequest(userRepository.findById(i.getAcceptBy()).orElseThrow());
                                a.setNoteLogSet(noteCatergorySet);
                                a.setEditReason(i.getRequestType().getRequestTypeName());

                                // lấy kis tự chấm công cũ
                                String[] signs = {"", ""};
                                if (a.getSigns() != null) {
                                    String[] s = a.getSigns().getName().toString().split("_");
                                    for (int j = 0; j < Math.min(s.length, signs.length); j++) {
                                        signs[j] = s[j];
                                    }
                                }

                                // buổi chiều có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        a.getTimeIn().isAfter(morningShift.getEndTime()) && a.getTimeOut().isAfter(afternoonShift.getStartTime())) {
                                    // buổi sáng có xin nghir phép
                                    if (signs[0].equals("P")) {
                                        i.setCheck(true);
                                        requestRepository.save(i);
                                        break;
                                    } else {
                                        noteLog.setSignChange(signRepository.findByName(ESign.KL_H));
                                        a.setSigns(signRepository.findByName(ESign.KL_H));
                                    }
                                }
                                // không chấm công
                                if (a.getTimeIn() == null && a.getTimeOut() == null) {

                                    // kiểm tra xem có xin nghỉ phép hay không

                                    // cả xin nghỉ phép cả ngày hoặc nghỉ phép buổi sáng
                                    if (signs[0].equals("P")) {
                                        i.setCheck(true);
                                        requestRepository.save(i);
                                        break;
                                    }
                                    // xin nghỉ phép buổi chiều
                                    else if (signs[1].equals("P")) {
                                        noteLog.setSignChange(signRepository.findByName(ESign.KL_P));
                                        a.setSigns(signRepository.findByName(ESign.KL_P));
//                                        LocalTime deltaTime = afternoonShift.getEndTime()
//                                                .minusHours(afternoonShift.getStartTime().getHour())
//                                                .minusMinutes(afternoonShift.getStartTime().getMinute())
//                                                .minusSeconds(afternoonShift.getStartTime().getSecond());
//                                        LocalTime regularHour = a.getRegularHour().plusHours(deltaTime.getHour()).plusMinutes(deltaTime.getMinute()).plusSeconds(deltaTime.getSecond());
//                                        a.setRegularHour(regularHour);
                                    } else {
                                        noteLog.setSignChange(signRepository.findByName(ESign.KL));
                                        a.setSigns(signRepository.findByName(ESign.KL));
                                        a.setRegularHour(null);
                                    }
                                }
                            }
                        }

                        // xin nghỉ buổi chiều: start và end không trước giờ bắt đầu buổi chiều
                        if (i.getStartTime().isAfter(morningShift.getEndTime()) && !i.getEndTime().isBefore(afternoonShift.getStartTime())) {
                            // kiểm tra xem có log attendance hay không
                            for (Attendance a : attendanceList) {

                                // buổi chiều có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        a.getTimeOut().isBefore(afternoonShift.getStartTime())) {
                                    // dừng duyệt request
                                    i.setCheck(true);
                                    requestRepository.save(i);
                                    break;
                                }

                                // lưu vào bảng note log
                                Set<NoteLog> noteCatergorySet = a.getNoteLogSet();
                                if (noteCatergorySet == null)
                                    noteCatergorySet = new HashSet<>();
                                NoteLog noteLog = new NoteLog();
                                noteLog.setAttendance(a);
                                noteLog.setNoteCategory(noteCatergoryRepository.findByName(ENoteCatergory.E_REQUEST));
                                noteLog.setContent(i.getRequestType().getRequestTypeName());
//                                    noteLog.setAdminEdit(userRepository.findByUserCode(editAttendances1.getCodeAdminEdit()));
                                if (a.getSigns() == null) {
                                    noteLog.setLastSign(null);
                                } else {
                                    noteLog.setLastSign(a.getSigns());
                                }
                                noteLog.setCreateDate(LocalDateTime.now());
//                                noteLog.setSignChange(new Sign(ESign.CĐ));
                                noteCatergorySet.add(noteLog);
                                noteLog.setApproversRequest(userRepository.findById(i.getAcceptBy()).orElseThrow());
                                a.setNoteLogSet(noteCatergorySet);
                                a.setEditReason(i.getRequestType().getRequestTypeName());

                                // lấy kis tự chấm công cũ
                                String[] signs = {"", ""};
                                if (a.getSigns() != null) {
                                    String[] s = a.getSigns().getName().toString().split("_");
                                    for (int j = 0; j < Math.min(s.length, signs.length); j++) {
                                        signs[j] = s[j];
                                    }
                                }

                                // buổi sáng có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        a.getTimeIn().isBefore(morningShift.getEndTime()) && a.getTimeOut().isBefore(afternoonShift.getStartTime())) {
                                    // buổi chiều nghỉ phép
                                    if (signs[1].equals("P")) {
                                        i.setCheck(true);
                                        requestRepository.save(i);
                                        break;
                                    } else {
                                        noteLog.setSignChange(signRepository.findByName(ESign.H_KL));
                                        a.setSigns(signRepository.findByName(ESign.H_KL));
                                    }
                                }

                                // không chấm công
                                if (a.getTimeIn() == null && a.getTimeOut() == null) {
                                    // cả ngày nghỉ phép hoặc buoir chiều nghỉ phép
                                    if (signs[1].equals("P") || (signs[0].equals("P") && signs[1].equals(""))) {
                                        i.setCheck(true);
                                        requestRepository.save(i);
                                        break;
                                    } else if (signs[0].equals("P")) {
                                        noteLog.setSignChange(signRepository.findByName(ESign.P_KL));
                                        a.setSigns(signRepository.findByName(ESign.P_KL));
//                                        LocalTime regularHour = morningShift.getEndTime().minusHours(morningShift.getStartTime().getHour())
//                                                .minusMinutes(morningShift.getStartTime().getMinute())
//                                                .minusSeconds(morningShift.getStartTime().getSecond());
//                                        a.setRegularHour(regularHour);
                                    } else {
                                        noteLog.setSignChange(signRepository.findByName(ESign.KL));
                                        a.setSigns(signRepository.findByName(ESign.KL));
                                    }

                                }
                            }
                        }

                        // xin nghỉ cả ngày: start không sau giờ kết thúc buổi sáng và end không trước giờ bắt đầu buổi chiều
                        if (!i.getStartTime().isAfter(morningShift.getEndTime()) && !i.getEndTime().isBefore(afternoonShift.getStartTime())) {
                            // kiểm tra xem có log attendance hay không
                            for (Attendance a : attendanceList) {

                                // cả ngày có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        a.getTimeIn().isBefore(morningShift.getEndTime()) && a.getTimeOut().isAfter(afternoonShift.getStartTime())) {
                                    i.setCheck(true);
                                    requestRepository.save(i);
                                    break;
                                }

                                // lưu vào bảng note log
                                Set<NoteLog> noteCatergorySet = a.getNoteLogSet();
                                if (noteCatergorySet == null)
                                    noteCatergorySet = new HashSet<>();
                                NoteLog noteLog = new NoteLog();
                                noteLog.setAttendance(a);
                                noteLog.setNoteCategory(noteCatergoryRepository.findByName(ENoteCatergory.E_REQUEST));
                                noteLog.setContent(i.getRequestType().getRequestTypeName());
//                                    noteLog.setAdminEdit(userRepository.findByUserCode(editAttendances1.getCodeAdminEdit()));
                                if (a.getSigns() == null) {
                                    noteLog.setLastSign(null);
                                } else {
                                    noteLog.setLastSign(a.getSigns());
                                }
                                noteLog.setCreateDate(LocalDateTime.now());
//                                noteLog.setSignChange(new Sign(ESign.CĐ));
                                noteCatergorySet.add(noteLog);
                                noteLog.setApproversRequest(userRepository.findById(i.getAcceptBy()).orElseThrow());
                                a.setNoteLogSet(noteCatergorySet);
                                a.setEditReason(i.getRequestType().getRequestTypeName());

                                // lấy kis tự chấm công cũ
                                String[] signs = {"", ""};
                                if (a.getSigns() != null) {
                                    String[] s = a.getSigns().getName().toString().split("_");
                                    for (int j = 0; j < Math.min(s.length, signs.length); j++) {
                                        signs[j] = s[j];
                                    }
                                }

                                // buổi chiều có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        a.getTimeIn().isAfter(morningShift.getEndTime()) && a.getTimeOut().isAfter(afternoonShift.getStartTime())) {
                                    noteLog.setSignChange(signRepository.findByName(ESign.KL_H));
                                    a.setSigns(signRepository.findByName(ESign.KL_H));
                                }

                                // buổi sáng có chấm công
                                if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                        a.getTimeIn().isBefore(morningShift.getEndTime()) && a.getTimeOut().isBefore(afternoonShift.getStartTime())) {
                                    noteLog.setSignChange(signRepository.findByName(ESign.H_KL));
                                    a.setSigns(signRepository.findByName(ESign.H_KL));
                                }

                                // không chấm công
                                if (a.getTimeIn() == null && a.getTimeOut() == null) {

                                    // cả ngày xin nghỉ phép
                                    if (signs[0].equals("P") && signs[1].equals("")) {
                                        i.setCheck(true);
                                        requestRepository.save(i);
                                        break;
                                    }
                                    // buổi sáng xin nghỉ phép
                                    else if (signs[0].equals("P")) {
                                        noteLog.setSignChange(signRepository.findByName(ESign.P_KL));
                                        a.setSigns(signRepository.findByName(ESign.P_KL));
                                    }
                                    // buổi chiều xin nghỉ phép
                                    else if (signs[1].equals("P")) {
                                        noteLog.setSignChange(signRepository.findByName(ESign.KL_P));
                                        a.setSigns(signRepository.findByName(ESign.KL_P));
                                    } else {
                                        noteLog.setSignChange(signRepository.findByName(ESign.KL));
                                        a.setSigns(signRepository.findByName(ESign.KL));
                                    }
                                }
                            }
                        }
                        i.setCheck(true);
                        requestRepository.save(i);
                        break;

                    case 3: // nghỉ chế độ  (đám cưới, đám tang,..)
                        for (Attendance a : attendanceList) {
                            // có chấm công
                            if (a.getTimeIn() != null && a.getTimeOut() != null) {
                                i.setCheck(true);
                                requestRepository.save(i);
                                break;
                            }
                            // không chấm công
                            if (a.getTimeIn() == null && a.getTimeOut() == null) {

                                Set<NoteLog> noteCatergorySet = a.getNoteLogSet();
                                if (noteCatergorySet == null)
                                    noteCatergorySet = new HashSet<>();
                                NoteLog noteLog = new NoteLog();
                                noteLog.setAttendance(a);
                                noteLog.setNoteCategory(noteCatergoryRepository.findByName(ENoteCatergory.E_REQUEST));
                                noteLog.setContent(i.getRequestType().getRequestTypeName());
//                                    noteLog.setAdminEdit(userRepository.findByUserCode(editAttendances1.getCodeAdminEdit()));
                                if (a.getSigns() == null) {
                                    noteLog.setLastSign(null);
                                } else {
                                    noteLog.setLastSign(a.getSigns());
                                }
                                noteLog.setCreateDate(LocalDateTime.now());
                                noteLog.setSignChange(signRepository.findByName(ESign.CĐ));
                                noteCatergorySet.add(noteLog);
                                noteLog.setApproversRequest(userRepository.findById(i.getAcceptBy()).orElseThrow());
                                a.setNoteLogSet(noteCatergorySet);
                                a.setEditReason(i.getRequestType().getRequestTypeName());

                                a.setSigns(signRepository.findByName(ESign.CĐ));
//                                LocalTime morning = morningShift.getEndTime()
//                                        .minusHours(morningShift.getStartTime().getHour())
//                                        .minusMinutes(morningShift.getStartTime().getMinute())
//                                        .minusSeconds(morningShift.getStartTime().getSecond());
//                                LocalTime afternoon = afternoonShift.getEndTime()
//                                        .minusHours(afternoonShift.getStartTime().getHour())
//                                        .minusMinutes(afternoonShift.getStartTime().getMinute())
//                                        .minusSeconds(afternoonShift.getStartTime().getSecond());
//                                LocalTime regularHour = morning.plusHours(afternoon.getHour())
//                                        .plusMinutes(afternoon.getMinute())
//                                        .plusSeconds(afternoon.getSecond());
//                                a.setRegularHour(regularHour);
                            }
                        }
                        i.setCheck(true);
                        requestRepository.save(i);
                        break;

                    case 4: // làm thêm giờ (xin trước)
                        // xin sau
                        // kiểm tra xem ngày duyệt đơn đã đi qua ngày xin ot trong đơn chưa
//                        if (!date.isBefore(i.getEndDate())) {
//                            for (Attendance a : attendanceList) {
//                                LocalTime overTime = LocalTime.of(0, 0, 0);
//                                if (!a.getTimeOut().isAfter(i.getEndTime())) {
//                                    overTime = a.getTimeOut().minusHours(i.getStartTime().getHour())
//                                            .minusMinutes(i.getStartTime().getMinute())
//                                            .minusSeconds(i.getStartTime().getSecond());
//                                } else {
//                                    overTime = i.getEndTime().minusHours(i.getStartTime().getHour())
//                                            .minusMinutes(i.getStartTime().getMinute())
//                                            .minusSeconds(i.getStartTime().getSecond());
//                                }
//                                a.setOverTime(overTime);
//                                LocalTime totalWork = a.getRegularHour().plusHours(overTime.getHour())
//                                        .plusMinutes(overTime.getMinute())
//                                        .plusSeconds(overTime.getSecond());
//                                a.setTotalWork(totalWork);
//                            }
                            i.setCheck(true);
                            requestRepository.save(i);
//                        }
                        break;

                    case 5: // làm thêm giờ (xin sau)
                        for (Attendance a : attendanceList) {
                            LocalTime overTime = LocalTime.of(0, 0, 0);
                            if(a.getTimeOut()!= null && a.getTimeOut().isAfter(afternoonShift.getEndTime())){
                                if (!a.getTimeOut().isAfter(i.getEndTime())) {
                                    overTime = a.getTimeOut().minusHours(i.getStartTime().getHour())
                                            .minusMinutes(i.getStartTime().getMinute())
                                            .minusSeconds(i.getStartTime().getSecond());
                                } else {
                                    overTime = i.getEndTime().minusHours(i.getStartTime().getHour())
                                            .minusMinutes(i.getStartTime().getMinute())
                                            .minusSeconds(i.getStartTime().getSecond());
                                }
                                a.setOverTime(overTime);
                                LocalTime totalWork = a.getRegularHour().plusHours(overTime.getHour())
                                        .plusMinutes(overTime.getMinute())
                                        .plusSeconds(overTime.getSecond());
                                a.setTotalWork(totalWork);
                            }
                        }
                        i.setCheck(true);
                        requestRepository.save(i);
                        break;

                    case 6: // quên chấm công
                        // giống đi công tác
                    case 7: // làm việc tại nhà
                        // giống đi công tác
                    case 8: // đi công tác

                        // xin điểm danh buổi sáng
                        if (!i.getStartTime().isAfter(morningShift.getEndTime()) && !i.getEndTime().isAfter(afternoonShift.getStartTime())) {
                            // kiểm tra xem ngày hôm đấy đã có log chưa
                            for (Attendance a : attendanceList) {
                                // lưu vào bảng note log
                                Set<NoteLog> noteCatergorySet = a.getNoteLogSet();
                                if (noteCatergorySet == null)
                                    noteCatergorySet = new HashSet<>();
                                NoteLog noteLog = new NoteLog();
                                noteLog.setAttendance(a);
                                noteLog.setNoteCategory(noteCatergoryRepository.findByName(ENoteCatergory.E_REQUEST));
                                noteLog.setContent(i.getRequestType().getRequestTypeName());
//                                    noteLog.setAdminEdit(userRepository.findByUserCode(editAttendances1.getCodeAdminEdit()));
                                if (a.getSigns() == null) {
                                    noteLog.setLastSign(null);
                                } else {
                                    noteLog.setLastSign(a.getSigns());
                                }
                                noteLog.setCreateDate(LocalDateTime.now());
//                                noteLog.setSignChange(new Sign(ESign.CĐ));
                                noteCatergorySet.add(noteLog);
                                noteLog.setApproversRequest(userRepository.findById(i.getAcceptBy()).orElseThrow());
                                a.setNoteLogSet(noteCatergorySet);
                                a.setEditReason(i.getRequestType().getRequestTypeName());

                                // lấy kis tự chấm công cũ
                                String[] signs = {"", ""};
                                if (a.getSigns() != null) {
                                    String[] s = a.getSigns().getName().toString().split("_");
                                    for (int j = 0; j < Math.min(s.length, signs.length); j++) {
                                        signs[j] = s[j];
                                    }
                                }

                                // nếu chưa có thì cập nhật timein, timeout, regular, sign
                                if (a.getTimeIn() == null && a.getTimeOut() == null) {
                                    // set timein timeout
                                    a.setTimeIn(i.getStartTime());
                                    a.setTimeOut(i.getEndTime());
                                    // tính regular
                                    LocalTime startTime = a.getTimeIn();
                                    LocalTime endTime = a.getTimeOut();
                                    // nếu checkin trước giờ bắt đầu ca sáng thì giờ làm được tính từ giờ bắt đầu ca sáng
                                    if (a.getTimeIn().isBefore(morningShift.getStartTime())) {
                                        startTime = morningShift.getStartTime();
                                    }
                                    // nếu checkout sau giờ kết thúc ca sangs thì giờ làm được tính đến giờ kết thúc ca sangs
                                    if (a.getTimeOut().isAfter(morningShift.getEndTime())) {
                                        endTime = morningShift.getEndTime();
                                    }
                                    // tính thời gian làm việc thực tế
                                    LocalTime regularHour = endTime
                                            .minusHours(startTime.getHour())
                                            .minusMinutes(startTime.getMinute())
                                            .minusSeconds(startTime.getSecond());
                                    a.setRegularHour(regularHour);
                                    a.setSigns(signRepository.findByName(ESign.H_KL));
                                    noteLog.setSignChange(signRepository.findByName(ESign.H_KL));
                                }
                                // nếu có rồi
                                if (a.getTimeIn() != null || a.getTimeOut() != null) {
                                    // kiểm tra timein và cập nhật timein
                                    if (a.getTimeIn() == null || a.getTimeIn().isAfter(i.getStartTime())) {
                                        a.setTimeIn(i.getStartTime());
                                    }
                                    // kiểm tra timeout và cập nhật timeout
                                    if (a.getTimeOut() == null || a.getTimeOut().isBefore(i.getEndTime())) {
                                        a.setTimeOut(i.getEndTime());
                                    }
                                    // câp nhật regular
                                    LocalTime startTime = a.getTimeIn();
                                    LocalTime endTime = a.getTimeOut();
                                    // nếu checkin trước giờ bắt đầu ca sáng thì giờ làm được tính từ giờ bắt đầu ca sáng
                                    if (a.getTimeIn().isBefore(morningShift.getStartTime())) {
                                        startTime = morningShift.getStartTime();
                                    }
                                    // nếu checkout buổi sáng
                                    if (!a.getTimeOut().isAfter(afternoonShift.getStartTime())) {
                                        // nếu checkout sau giờ kết thúc ca sangs thì giờ làm được tính đến giờ kết thúc ca sangs
                                        if (a.getTimeOut().isAfter(afternoonShift.getEndTime())) {
                                            endTime = morningShift.getEndTime();
                                        }
                                        LocalTime regularHour = endTime
                                                .minusHours(startTime.getHour())
                                                .minusMinutes(startTime.getMinute())
                                                .minusSeconds(startTime.getSecond());
                                        a.setRegularHour(regularHour);
                                        // nếu buổi chiều đã xin nghỉ phép
                                        if (signs[1].equals("P")) {
                                            a.setSigns(signRepository.findByName(ESign.H_P));
                                            noteLog.setSignChange(signRepository.findByName(ESign.H_P));
                                        }
                                        // nếu buổi chiều chưa xin nghỉ phép
                                        else {
                                            a.setSigns(signRepository.findByName(ESign.H_KL));
                                            noteLog.setSignChange(signRepository.findByName(ESign.H_KL));
                                        }
                                    }
                                    // nếu checkout buổi chiều
                                    else {
                                        // nếu checkout sau giờ kết thúc ca chiều thì giờ làm được tính đến giờ kết thúc ca chiều
                                        if (a.getTimeOut().isAfter(afternoonShift.getEndTime())) {
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
                                        a.setRegularHour(regularHour);
                                        a.setSigns(signRepository.findByName(ESign.H));
                                        noteLog.setSignChange(signRepository.findByName(ESign.H));
                                    }
                                }
                            }
                        }

                        // xin điểm danh buổi chiều
                        if (i.getStartTime().isAfter(morningShift.getEndTime()) && !i.getEndTime().isBefore(afternoonShift.getStartTime())) {
                            // kiểm tra xem ngày hôm đấy đã có log chưa
                            for (Attendance a : attendanceList) {
                                // lưu vào bảng note log
                                Set<NoteLog> noteCatergorySet = a.getNoteLogSet();
                                if (noteCatergorySet == null)
                                    noteCatergorySet = new HashSet<>();
                                NoteLog noteLog = new NoteLog();
                                noteLog.setAttendance(a);
                                noteLog.setNoteCategory(noteCatergoryRepository.findByName(ENoteCatergory.E_REQUEST));
                                noteLog.setContent(i.getRequestType().getRequestTypeName());
//                                    noteLog.setAdminEdit(userRepository.findByUserCode(editAttendances1.getCodeAdminEdit()));
                                if (a.getSigns() == null) {
                                    noteLog.setLastSign(null);
                                } else {
                                    noteLog.setLastSign(a.getSigns());
                                }
                                noteLog.setCreateDate(LocalDateTime.now());
//                                noteLog.setSignChange(new Sign(ESign.CĐ));
                                noteCatergorySet.add(noteLog);
                                noteLog.setApproversRequest(userRepository.findById(i.getAcceptBy()).orElseThrow());
                                a.setNoteLogSet(noteCatergorySet);
                                a.setEditReason(i.getRequestType().getRequestTypeName());

                                // lấy kis tự chấm công cũ
                                String[] signs = {"", ""};
                                if (a.getSigns() != null) {
                                    String[] s = a.getSigns().getName().toString().split("_");
                                    for (int j = 0; j < Math.min(s.length, signs.length); j++) {
                                        signs[j] = s[j];
                                    }
                                }

                                // nếu chưa có thì cập nhật timein, timeout, regular, sign
                                if (a.getTimeIn() == null && a.getTimeOut() == null) {
                                    // set timein timeout
                                    a.setTimeIn(i.getStartTime());
                                    a.setTimeOut(i.getEndTime());
                                    // tính regular
                                    LocalTime startTime = a.getTimeIn();
                                    LocalTime endTime = a.getTimeOut();
                                    // nếu checkin trước giờ bắt đầu ca chiều thì giờ làm được tính từ giờ bắt đầu ca chiều
                                    if (a.getTimeIn().isBefore(afternoonShift.getStartTime())) {
                                        startTime = afternoonShift.getStartTime();
                                    }
                                    // nếu checkout sau giờ kết thúc ca chiều thì giờ làm được tính đến giờ kết thúc ca chiều
                                    if (a.getTimeOut().isAfter(afternoonShift.getEndTime())) {
                                        endTime = afternoonShift.getEndTime();
                                    }
                                    // tính thời gian làm việc thực tế
                                    LocalTime regularHour = endTime
                                            .minusHours(startTime.getHour())
                                            .minusMinutes(startTime.getMinute())
                                            .minusSeconds(startTime.getSecond());
                                    a.setRegularHour(regularHour);
                                    a.setSigns(signRepository.findByName(ESign.KL_H));
                                    noteLog.setSignChange(signRepository.findByName(ESign.KL_H));
                                }

                                // nếu có rồi
                                if (a.getTimeIn() != null || a.getTimeOut() != null) {
                                    // kiểm tra timein và cập nhật timein
                                    if (a.getTimeIn() == null || a.getTimeIn().isAfter(i.getStartTime())) {
                                        a.setTimeIn(i.getStartTime());
                                    }
                                    // kiểm tra timeout và cập nhật timeout
                                    if (a.getTimeOut() == null || a.getTimeOut().isBefore(i.getEndTime())) {
                                        a.setTimeOut(i.getEndTime());
                                    }
                                    // câp nhật regular
                                    LocalTime startTime = a.getTimeIn();
                                    LocalTime endTime = a.getTimeOut();
                                    // nếu checkout sau giờ kết thúc ca chiều thì giờ làm được tính đến giờ kết thúc ca chiều
                                    if (a.getTimeOut().isAfter(afternoonShift.getEndTime())) {
                                        endTime = afternoonShift.getEndTime();
                                    }
                                    // nếu checkin buổi chiều
                                    if (!a.getTimeIn().isBefore(morningShift.getEndTime())) {
                                        // nếu checkin trước giờ bắt đầu ca chiều thì giờ làm được tính từ giờ bắt đầu ca chiều
                                        if (a.getTimeIn().isBefore(afternoonShift.getStartTime())) {
                                            startTime = afternoonShift.getStartTime();
                                        }
                                        LocalTime regularHour = endTime
                                                .minusHours(startTime.getHour())
                                                .minusMinutes(startTime.getMinute())
                                                .minusSeconds(startTime.getSecond());
                                        a.setRegularHour(regularHour);
                                        // nếu buổi sáng đã xin nghỉ phép
                                        if (signs[0].equals("P")) {
                                            a.setSigns(signRepository.findByName(ESign.P_H));
                                            noteLog.setSignChange(signRepository.findByName(ESign.P_H));
                                        }
                                        // nếu buổi sáng chưa xin nghỉ phép
                                        else {
                                            a.setSigns(signRepository.findByName(ESign.KL_H));
                                            noteLog.setSignChange(signRepository.findByName(ESign.KL_H));
                                        }
                                    }
                                    // nếu checkin buổi sáng
                                    else {
                                        // nếu checkin trước giờ bắt đầu ca sáng thì giờ làm được tính từ giờ bắt đầu ca sáng
                                        if (a.getTimeIn().isBefore(morningShift.getStartTime())) {
                                            startTime = morningShift.getStartTime();
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
                                        a.setRegularHour(regularHour);
                                        a.setSigns(signRepository.findByName(ESign.H));
                                        noteLog.setSignChange(signRepository.findByName(ESign.H));
                                    }
                                }
                            }
                        }

                        // xin điểm danh cả ngày
                        if (!i.getStartTime().isAfter(morningShift.getEndTime()) && !i.getEndTime().isBefore(afternoonShift.getStartTime())) {
                            // kiểm tra xem ngày hôm đấy có log chấm công hay chưa
                            for (Attendance a : attendanceList) {
                                // lưu vào bảng note log
                                Set<NoteLog> noteCatergorySet = a.getNoteLogSet();
                                if (noteCatergorySet == null)
                                    noteCatergorySet = new HashSet<>();
                                NoteLog noteLog = new NoteLog();
                                noteLog.setAttendance(a);
                                noteLog.setNoteCategory(noteCatergoryRepository.findByName(ENoteCatergory.E_REQUEST));
                                noteLog.setContent(i.getRequestType().getRequestTypeName());
//                                    noteLog.setAdminEdit(userRepository.findByUserCode(editAttendances1.getCodeAdminEdit()));
                                if (a.getSigns() == null) {
                                    noteLog.setLastSign(null);
                                } else {
                                    noteLog.setLastSign(a.getSigns());
                                }
                                noteLog.setCreateDate(LocalDateTime.now());
//                                noteLog.setSignChange(new Sign(ESign.CĐ));
                                noteCatergorySet.add(noteLog);
                                noteLog.setApproversRequest(userRepository.findById(i.getAcceptBy()).orElseThrow());
                                a.setNoteLogSet(noteCatergorySet);
                                a.setEditReason(i.getRequestType().getRequestTypeName());

                                // lấy kis tự chấm công cũ
                                String[] signs = {"", ""};
                                if (a.getSigns() != null) {
                                    String[] s = a.getSigns().toString().split("_");
                                    for (int j = 0; j < Math.min(s.length, signs.length); j++) {
                                        signs[j] = s[j];
                                    }
                                }

                                // nếu chưa có thì cập nhật timein, timeout, regular, sign
                                if (a.getTimeIn() == null && a.getTimeOut() == null) {
                                    // set timein timeout
                                    a.setTimeIn(i.getStartTime());
                                    a.setTimeOut(i.getEndTime());
                                    // tính regular
                                    LocalTime startTime = a.getTimeIn();
                                    LocalTime endTime = a.getTimeOut();
                                    // nếu checkin trước giờ bắt đầu ca sáng thì giờ làm được tính từ giờ bắt đầu ca sáng
                                    if (a.getTimeIn().isBefore(morningShift.getStartTime())) {
                                        startTime = morningShift.getStartTime();
                                    }
                                    // nếu checkout sau giờ kết thúc ca sangs thì giờ làm được tính đến giờ kết thúc ca sangs
                                    if (a.getTimeOut().isAfter(morningShift.getEndTime())) {
                                        endTime = morningShift.getEndTime();
                                    }
                                    // tính thời gian làm việc thực tế
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
                                    a.setRegularHour(regularHour);
                                    a.setSigns(signRepository.findByName(ESign.H));
                                    noteLog.setSignChange(signRepository.findByName(ESign.H));
                                }

                                // nếu có rồi
                                if (a.getTimeIn() != null || a.getTimeOut() != null) {
                                    // kiểm tra timein và cập nhật timein
                                    if (a.getTimeIn() == null || a.getTimeIn().isAfter(i.getStartTime())) {
                                        a.setTimeIn(i.getStartTime());
                                    }
                                    // kiểm tra timeout và cập nhật timeout
                                    if (a.getTimeOut() == null || a.getTimeOut().isBefore(i.getEndTime())) {
                                        a.setTimeOut(i.getEndTime());
                                    }
                                    // câp nhật regular
                                    LocalTime startTime = a.getTimeIn();
                                    LocalTime endTime = a.getTimeOut();
                                    // tính thời gian làm việc thực tế
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
                                    a.setRegularHour(regularHour);
                                    a.setSigns(signRepository.findByName(ESign.H));
                                    noteLog.setSignChange(signRepository.findByName(ESign.H));
                                }
                            }
                        }

                        i.setCheck(true);
                        requestRepository.save(i);
                        break;
                }
                // save attendance
                for (Attendance a : attendanceList) {
                    attendanceRepository.save(a);
                }
            }
        }
        return requestList;
    }

    @Override
    public Page<RequestResponse> getListByUserIdAndStartDate(String startDate,Pageable pageable) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String dateInput = startDate == null || startDate.equals("") ? null : startDate;

        Page<Request> list = requestRepository.getListByUserIdAndStartDate(dateInput,userDetails.getId(),pageable);

        Page<RequestResponse> result = list.map(new Function<Request, RequestResponse>() {
            @Override
            public RequestResponse apply(Request entity) {

                RequestResponse dto = new RequestResponse();
                // Conversion logic
                dto.setId(entity.getId());
                dto.setRequestContent(entity.getRequestContent());
                dto.setRequestTitle(entity.getRequestTitle());
                dto.setCreatedBy(entity.getCreatedBy());
                dto.setCreatedDate(entity.getCreatedDate());
                dto.setEndDate(entity.getEndDate());
                dto.setEndTime(entity.getEndTime());
                dto.setRequestTypeId(entity.getRequestType().getId());
                dto.setStartDate(entity.getStartDate());
                dto.setStartTime(entity.getStartTime());
                dto.setUpdatedBy(entity.getUpdatedBy());
                dto.setUpdatedDate(entity.getUpdatedDate());
                dto.setUserId(entity.getUser().getId());
                dto.setStatus(entity.getStatus());
                dto.setDepartmentId(Math.toIntExact(entity.getUser().getDepartment().getId()));
                dto.setDepartment(entity.getUser().getDepartment());
                dto.setUser(entity.getUser());
                dto.setNote(entity.getNote());
                dto.setRequestType(entity.getRequestType());
                return dto;
            }
        });
        return result;
    }
}
