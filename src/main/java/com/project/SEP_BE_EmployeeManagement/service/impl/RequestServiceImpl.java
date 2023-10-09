package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateRequestReq;
import com.project.SEP_BE_EmployeeManagement.dto.response.holiday.HolidayResponse;
import com.project.SEP_BE_EmployeeManagement.dto.response.request.RequestRes;
import com.project.SEP_BE_EmployeeManagement.model.Holiday;
import com.project.SEP_BE_EmployeeManagement.model.Request;
import com.project.SEP_BE_EmployeeManagement.repository.RequestRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.security.jwt.UserDetailsImpl;
import com.project.SEP_BE_EmployeeManagement.service.RequestService;
import com.project.SEP_BE_EmployeeManagement.service.RequestTypeService;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
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


    @Override
    public Request createRequest(CreateRequestReq request) {

//        parse localDate to date
        LocalDate localDate = LocalDate.now();
        Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Request obj = new Request();
        obj.setRequestContent(request.getRequestContent());
        obj.setRequestTitle(request.getRequestTitle());
        obj.setRequestContent(request.getRequestContent());
        obj.setStatus(request.isStatus());
        obj.setEndDate(request.getEndDate());
        obj.setStartDate(request.getStartDate());
//        obj.setStartTime(request.getStartTime());
//        obj.setEndTime(request.getEndTime());
        obj.setCreatedBy(userDetails.getId().toString());
        obj.setCreatedDate(currentDate);
        obj.setRequestType(requestTypeService.findById(request.getRequestTypeId()));
        obj.setUser(userRepository.findById(userDetails.getId()).get());

        requestRepository.save(obj);
        return obj;
    }

    @Override
    public Request updateRequest(CreateRequestReq request, int id) {
        LocalDate localDate = LocalDate.now();
        Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Request obj = requestRepository.findById(id);
        if(obj == null){
            throw new RuntimeException("Không tìm thấy yê cầu");
        }


        obj.setRequestContent(request.getRequestContent());
        obj.setRequestTitle(request.getRequestTitle());
        obj.setRequestContent(request.getRequestContent());
        obj.setStatus(request.isStatus());
        obj.setEndDate(request.getEndDate());
        obj.setStartDate(request.getStartDate());
//        obj.setStartTime(request.getStartTime());
//        obj.setEndTime(request.getEndTime());
        obj.setCreatedBy(userDetails.getId().toString());
        obj.setCreatedDate(currentDate);
        obj.setRequestType(requestTypeService.findById(request.getRequestTypeId()));
        obj.setUser(userRepository.findById(userDetails.getId()).get());

        requestRepository.save(obj);
        return obj;
    }

    @Override
    public Request findById(int id) {
        return requestRepository.findById(id);
    }

    @Override
    public Page<RequestRes> getList(String searchInput, Pageable pageable) {
        String search = searchInput == null || searchInput.toString() == "" ? "" : searchInput;
        Page<Request> list = requestRepository.getList( search,pageable);

        Page<RequestRes> result = list.map(new Function<Request, RequestRes>() {
            @Override
            public RequestRes apply(Request entity) {

                RequestRes dto = new RequestRes();
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

                return dto;
            }
        });
        return  result;
    }
}
