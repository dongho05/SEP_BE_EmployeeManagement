package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateReqRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.request.RequestResponse;
import com.project.SEP_BE_EmployeeManagement.model.Request;
import com.project.SEP_BE_EmployeeManagement.repository.RequestRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.security.jwt.UserDetailsImpl;
import com.project.SEP_BE_EmployeeManagement.service.DepartmentService;
import com.project.SEP_BE_EmployeeManagement.service.RequestService;
import com.project.SEP_BE_EmployeeManagement.service.RequestTypeService;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
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
}
