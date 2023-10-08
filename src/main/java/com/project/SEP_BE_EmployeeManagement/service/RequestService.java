package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateRequestReq;
import com.project.SEP_BE_EmployeeManagement.model.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface RequestService {

    Request createRequest(CreateRequestReq request);
    Request updateRequest(CreateRequestReq request, int id);
    Request findById(int id);
    Page<Request> getList(String search, Pageable pageable);
}
