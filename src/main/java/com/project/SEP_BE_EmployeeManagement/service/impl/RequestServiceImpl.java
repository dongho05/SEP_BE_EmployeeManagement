package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateRequestReq;
import com.project.SEP_BE_EmployeeManagement.model.Request;
import com.project.SEP_BE_EmployeeManagement.repository.RequestRepository;
import com.project.SEP_BE_EmployeeManagement.service.RequestService;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    UserService userService;
    @Override
    public Request createRequest(CreateRequestReq request) {

//        parse localDate to date
        LocalDate localDate = LocalDate.now();
        Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Request obj = new Request();
        obj.setRequestContent(request.getRequestContent());
        obj.setRequestTitle(request.getRequestTitle());
//        LocalDate.now()
        obj.setRequestContent(request.getRequestContent());
        obj.setStatus(request.isStatus());
        obj.setEndDate(request.getEndDate());
        obj.setStartDate(request.getStartDate());
//        obj.setStartTime(request.getStartTime());
//        obj.setEndTime(request.getEndTime());
        obj.setCreatedBy(request.getCreatedBy());
        obj.setCreatedDate(currentDate);

        requestRepository.save(obj);
        return obj;
    }

    @Override
    public Request updateRequest(CreateRequestReq request, int id) {
        return null;
    }

    @Override
    public Request findById(int id) {
        return null;
    }

    @Override
    public Page<Request> getList(String searchInput, Pageable pageable) {
        String search = searchInput == null || searchInput.toString() == "" ? "" : searchInput;
        Page<Request> list = requestRepository.getList( search,pageable);
        return  list;
    }
}
