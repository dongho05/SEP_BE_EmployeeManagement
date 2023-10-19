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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

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
        obj.setRequestContent(request.getRequestContent());
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
        if(obj == null){
            throw new RuntimeException("Không tìm thấy yêu cầu");
        }

        obj.setRequestContent(request.getRequestContent());
        obj.setRequestTitle(request.getRequestTitle());
        obj.setRequestContent(request.getRequestContent());
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
    public Page<RequestResponse> getList(String searchInput, Pageable pageable, int statusReq) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String search = searchInput == null || searchInput.toString() == "" ? "" : searchInput;

        Page<Request> list =null;

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        boolean isAdmin = hasRoleAdmin(authorities);
        boolean isMod = hasRoleMod(authorities);
        if(isAdmin){
             list = requestRepository.getList( search,pageable,null,null,statusReq);
        }else if(isMod){
            list = requestRepository.getList( search,pageable,userDetails.getId(),userService.findByUsernameOrEmail(userDetails.getUsername()).get().getDepartment().getId(),statusReq);
        }
        else{
            list = requestRepository.getList( search,pageable,userDetails.getId(),null,statusReq);
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

                return dto;
            }
        });
        return  result;
    }

//    1: Đang xử lý, 2: Chấp nhận, 3: Từ chối
    @Override
    public void updateStatusRequest(long requestId, int statusRequest) {
        LocalDate localDate = LocalDate.now();
        Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Request obj = requestRepository.findById(requestId);
        obj.setStatus(statusRequest);
        obj.setAcceptBy(userDetails.getId());
        obj.setAcceptAt(localDate);
        requestRepository.save(obj);
    }
}
