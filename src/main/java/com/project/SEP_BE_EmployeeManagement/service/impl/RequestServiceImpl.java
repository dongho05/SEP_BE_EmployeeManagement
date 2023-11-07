package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateReqRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.request.RequestResponse;
import com.project.SEP_BE_EmployeeManagement.model.*;
import com.project.SEP_BE_EmployeeManagement.repository.AttendanceRepository;
import com.project.SEP_BE_EmployeeManagement.repository.RequestRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.repository.WorkingTimeRepository;
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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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


    @Override
    public Request createRequest(CreateReqRequest request) {

//        parse localDate to date
        LocalDate localDate = LocalDate.now();
        Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Request obj = new Request();
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

    @Override
    public Request updateRequest(CreateReqRequest request, long id) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Request obj = requestRepository.findById(id);
        if (obj == null) {
            throw new RuntimeException("Không tìm thấy yêu cầu");
        }

        obj.setRequestContent(request.getRequestContent());
        obj.setRequestTitle(request.getRequestTitle());
        obj.setEndDate(request.getEndDate());
        obj.setStartDate(request.getStartDate());
        obj.setStartTime(request.getStartTime());
        obj.setEndTime(request.getEndTime());
        obj.setRequestType(requestTypeService.findById(request.getRequestTypeId()));
        obj.setUser(userRepository.findById(userDetails.getId()).get());

        requestRepository.save(obj);
        return obj;
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
    public Page<RequestResponse> getList(String searchInput, String departmentId, int statusReq, String fromDate, String toDate, Pageable pageable) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String search = searchInput == null || searchInput.toString() == "" ? "" : searchInput;
        String did = departmentId == null || departmentId.toString() == "" ? "" : departmentId;
        String from = fromDate == null || fromDate.equals("") ? null : fromDate;
        String to = toDate == null || toDate.equals("") ? null : toDate;

        Page<Request> list = null;

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        boolean isAdmin = hasRoleAdmin(authorities);
        boolean isMod = hasRoleMod(authorities);
        if (isAdmin) {
            list = requestRepository.getList(search
                    , did
                    , statusReq
                    , null
                    , from
                    , to
                    , pageable);
        } else if (isMod) {
            list = requestRepository.getList(search
                    , userService.findByUsernameOrEmail(userDetails.getUsername()).get().getDepartment().getId().toString()
                    , statusReq
                    , null
                    , from
                    , to
                    , pageable);
        } else {
            list = requestRepository.getList(search, null, statusReq, userDetails.getId(), from, to, pageable);
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
    public Page<RequestResponse> getListByUserId(String searchInput, int statusReq, String fromDate, String toDate, Pageable pageable) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String search = searchInput == null || searchInput.toString() == "" ? "" : searchInput;
        String from = fromDate == null || fromDate.equals("") ? null : fromDate;
        String to = toDate == null || toDate.equals("") ? null : toDate;

        Page<Request> list = requestRepository.getList(search, null, statusReq, userDetails.getId(), from, to, pageable);

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
        Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Request obj = requestRepository.findById(requestId);
        obj.setStatus(statusRequest);
        obj.setAcceptBy(userDetails.getId());
        obj.setAcceptAt(localDate);
        obj.setNote(note);
        requestRepository.save(obj);
    }

    @Override
    @Scheduled(cron = "0 0 20 L * ?") // chạy vào 20h mỗi ngày
    public List<Request> processRequestOnMonth() {
        LocalDate date = LocalDate.of(2023, 10, 31);
//        LocalDate date = LocalDate.now();
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        List<Request> requestList = new ArrayList<>();
        // kiểm tra xem ngày có phải là ngày cuối tháng hay không
        if (date.equals(lastDayOfMonth)) {
            // lấy danh sách tất cả các request được accept theo date
            requestList = requestRepository.findRequestsAcceptedInCurrentMonth(date);
            if(requestList.size() > 0){
                // duyệt danh sách request
                for (Request i : requestList){
                    // list các attendance liên quan đến request
                    List<Attendance> attendanceList = new ArrayList<>();
                    // duyệt qua các ngày trong request
                    LocalDate startDate = i.getStartDate();
                    LocalDate endDate = i.getEndDate();
                    LocalDate currentDate = startDate;
                    while (!currentDate.isAfter(endDate)) {
                        Attendance attendance = attendanceRepository.findAttendanceByUserAndDate(i.getUser().getId(), currentDate);
                        if(attendance == null){
                            attendance = new Attendance(i.getUser(), currentDate);
                        }
                        attendanceList.add(attendance);
                        // Di chuyển đến ngày tiếp theo
                        currentDate = currentDate.plusDays(1);
                    }
                    WorkingTime morningShift = workingTimeRepository.findByWorkingTimeName(EWorkingTime.MORNING_SHIFT).orElseThrow();
                    WorkingTime afternoonShift = workingTimeRepository.findByWorkingTimeName(EWorkingTime.AFTERNOON_SHIFT).orElseThrow();
                    int checkRequestType = i.getRequestType().getId();
                    switch (checkRequestType){
                        case 1: // nghỉ có lương
                            // xin nghỉ buổi sáng: start và end không sau giờ kết thúc buổi sáng
                            if(!i.getStartTime().isAfter(morningShift.getEndTime()) && !i.getEndTime().isAfter(morningShift.getEndTime())){
                                // kiểm tra xem có log attendance hay không
                                for(Attendance a : attendanceList){
                                    if(a.getTimeIn() != null && a.getTimeOut() != null &&
                                            a.getTimeIn().isAfter(morningShift.getEndTime()) && a.getTimeOut().isAfter(afternoonShift.getStartTime())){
                                        a.setSigns(new Sign(ESign.P_H));
                                    }
                                    if(a.getTimeIn() == null && a.getTimeOut() == null){
                                        a.setSigns(new Sign(ESign.P_KL));
                                    }
                                }
                            }
                            // xin nghỉ buổi chiều: start và end không trước giờ bắt đầu buổi chiều
                            if(!i.getStartTime().isBefore(afternoonShift.getStartTime()) && !i.getEndTime().isBefore(afternoonShift.getStartTime())){
                                // kiểm tra xem có log attendance hay không
                                for(Attendance a : attendanceList){
                                    if(a.getTimeIn() != null && a.getTimeOut() != null &&
                                            a.getTimeIn().isBefore(morningShift.getEndTime()) && a.getTimeOut().isBefore(afternoonShift.getStartTime())){
                                        a.setSigns(new Sign(ESign.H_P));
                                    }
                                    if(a.getTimeIn() == null && a.getTimeOut() == null){
                                        a.setSigns(new Sign(ESign.KL_P));
                                    }
                                }
                            }
                            // xin nghỉ cả ngày: start không sau giờ kết thúc buổi sáng và end không trước giờ bắt đầu buổi chiều
                            if(!i.getStartTime().isAfter(morningShift.getEndTime()) && !i.getEndTime().isBefore(afternoonShift.getStartTime())){
                                // kiểm tra xem có log attendance hay không
                                for(Attendance a : attendanceList){
                                    if(a.getTimeIn() != null && a.getTimeOut() != null &&
                                            a.getTimeIn().isAfter(morningShift.getEndTime()) && a.getTimeOut().isAfter(afternoonShift.getStartTime())){
                                        a.setSigns(new Sign(ESign.P_H));
                                    }
                                    if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                            a.getTimeIn().isBefore(morningShift.getEndTime()) && a.getTimeOut().isBefore(afternoonShift.getStartTime())) {
                                        a.setSigns(new Sign(ESign.H_P));
                                    }
                                    if(a.getTimeIn() == null && a.getTimeOut() == null){
                                        a.setSigns(new Sign(ESign.P));
                                    }
                                }
                            }
                            break;
                        case 2: // nghỉ không lương
                            // xin nghỉ buổi sáng: start và end không sau giờ kết thúc buổi sáng
                            if(!i.getStartTime().isAfter(morningShift.getEndTime()) && !i.getEndTime().isAfter(morningShift.getEndTime())){
                                // kiểm tra xem có log attendance hay không
                                for(Attendance a : attendanceList){
                                    if(a.getTimeIn() != null && a.getTimeOut() != null &&
                                            a.getTimeIn().isAfter(morningShift.getEndTime()) && a.getTimeOut().isAfter(afternoonShift.getStartTime())){
                                        a.setSigns(new Sign(ESign.KL_H));
                                    }
                                    if(a.getTimeIn() == null && a.getTimeOut() == null){
                                        a.setSigns(new Sign(ESign.KL));
                                    }
                                }
                            }
                            // xin nghỉ buổi chiều: start và end không trước giờ bắt đầu buổi chiều
                            if(!i.getStartTime().isBefore(afternoonShift.getStartTime()) && !i.getEndTime().isBefore(afternoonShift.getStartTime())){
                                // kiểm tra xem có log attendance hay không
                                for(Attendance a : attendanceList){
                                    if(a.getTimeIn() != null && a.getTimeOut() != null &&
                                            a.getTimeIn().isBefore(morningShift.getEndTime()) && a.getTimeOut().isBefore(afternoonShift.getStartTime())){
                                        a.setSigns(new Sign(ESign.H_KL));
                                    }
                                    if(a.getTimeIn() == null && a.getTimeOut() == null){
                                        a.setSigns(new Sign(ESign.KL));
                                    }
                                }
                            }
                            // xin nghỉ cả ngày: start không sau giờ kết thúc buổi sáng và end không trước giờ bắt đầu buổi chiều
                            if(!i.getStartTime().isAfter(morningShift.getEndTime()) && !i.getEndTime().isBefore(afternoonShift.getStartTime())){
                                // kiểm tra xem có log attendance hay không
                                for(Attendance a : attendanceList){
                                    if(a.getTimeIn() != null && a.getTimeOut() != null &&
                                            a.getTimeIn().isAfter(morningShift.getEndTime()) && a.getTimeOut().isAfter(afternoonShift.getStartTime())){
                                        a.setSigns(new Sign(ESign.KL_H));
                                    }
                                    if (a.getTimeIn() != null && a.getTimeOut() != null &&
                                            a.getTimeIn().isBefore(morningShift.getEndTime()) && a.getTimeOut().isBefore(afternoonShift.getStartTime())) {
                                        a.setSigns(new Sign(ESign.H_KL));
                                    }
                                    if(a.getTimeIn() == null && a.getTimeOut() == null){
                                        a.setSigns(new Sign(ESign.KL));
                                    }
                                }
                            }
                            break;
                        case 3: // nghỉ chế độ  (đám cưới, đám tang,..)
                            for(Attendance a : attendanceList){
                                if(a.getTimeIn() == null && a.getTimeOut() == null){
                                    a.setSigns(new Sign(ESign.CĐ));
                                }
                            }
                            break;
                        case 4: // làm thêm giờ (xin trước)
                            // xin sau
                            break;
                        case 5: // làm thêm giờ (xin sau)
                            for(Attendance a : attendanceList){
                                LocalTime overTime = LocalTime.of(0, 0, 0);
                                if(!a.getTimeOut().isAfter(i.getEndTime())){
                                    overTime = a.getTimeOut().minusHours(i.getStartTime().getHour())
                                            .minusMinutes(i.getStartTime().getMinute())
                                            .minusSeconds(i.getStartTime().getSecond());
                                }else{
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
                            break;
                        case 6: // quên chấm công
                            for(Attendance a : attendanceList){
                                a.setTimeIn(i.getStartTime());
                                a.setTimeOut(i.getEndTime());
                                // set RegularHour
                                // nếu làm cả ngày RegularHour phải trừ thời gian nghỉ trưa
                                if (a.getTimeIn().isBefore(morningShift.getEndTime()) && a.getTimeOut().isAfter(afternoonShift.getStartTime())) {

                                    LocalTime startTime = a.getTimeIn();
                                    LocalTime endTime = a.getTimeOut();
                                    // nếu checkin trước gờ bắt đầu ca sáng thì giờ làm được tính từ giờ bắt đầu ca sáng
                                    if (a.getTimeIn().isBefore(morningShift.getStartTime())) {
                                        startTime = morningShift.getStartTime();
                                    }
                                    // nếu checkout sau giờ kết thúc ca chiều thì giờ làm được tính đến giờ kết thúc ca chiêều
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
                                    a.setSigns(new Sign(ESign.H));
                                }
                                // nếu làm nửa ngày thì RegularHour không phải trừ thời gian nghỉ trưa
                                if (
                                        (a.getTimeIn().isAfter(morningShift.getEndTime()) && a.getTimeOut().isAfter(afternoonShift.getStartTime())) ||
                                                (a.getTimeIn().isBefore(morningShift.getEndTime()) && a.getTimeOut().isBefore(afternoonShift.getStartTime()))
                                ) {
                                    LocalTime startTime = a.getTimeIn();
                                    LocalTime endTime = a.getTimeOut();
                                    // nếu checkin sớm thì thời gian đi làm được tính từ thời gian bắt đầu ca
                                    if (startTime.isBefore(morningShift.getStartTime())) {
                                        startTime = morningShift.getStartTime();
                                    }
                                    if (startTime.isBefore(afternoonShift.getStartTime())) {
                                        startTime = afternoonShift.getStartTime();
                                    }
                                    // nếu checkout muộn thì thời gian đi làm được tính đến thời gian kết thúc quy định
                                    if (endTime.isAfter(morningShift.getEndTime())) {
                                        endTime = morningShift.getEndTime();
                                    }
                                    if (endTime.isAfter(afternoonShift.getEndTime())) {
                                        endTime = afternoonShift.getEndTime();
                                    }
                                    LocalTime regularHour = endTime
                                            .minusHours(startTime.getHour())
                                            .minusMinutes(startTime.getMinute())
                                            .minusSeconds(startTime.getSecond());
                                    a.setRegularHour(regularHour);
                                    // set sign
                                    if(a.getTimeIn().isBefore(morningShift.getEndTime())){
                                        a.setSigns(new Sign(ESign.H_KL));
                                    }
                                    if (a.getTimeIn().isAfter(morningShift.getEndTime())) {
                                        a.setSigns(new Sign(ESign.KL_H));
                                    }
                                }
                                // set totalWork
                                if (a.getOverTime() == null) {
                                    a.setTotalWork(a.getRegularHour());
                                } else {
                                    a.setTotalWork(a.getRegularHour()
                                            .plusHours(a.getOverTime().getHour())
                                            .plusMinutes(a.getOverTime().getMinute())
                                            .plusSeconds(a.getOverTime().getSecond()));
                                }
                            }
                            break;
                        case 7: // làm việc tại nhà
                            // giống quên chấm công
                            break;
                        case 8: // đi công tác
                            // giống quên chấm công
                            break;
                    }
                    // save attendance
                    for(Attendance a : attendanceList){
                        attendanceRepository.save(a);
                    }
                }
            }
        }
        return requestList;
    }
}
