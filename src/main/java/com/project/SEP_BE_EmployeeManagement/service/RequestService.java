package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateReqRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.request.RequestResponse;
import com.project.SEP_BE_EmployeeManagement.model.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface RequestService {

    Request createRequest(CreateReqRequest request);
    Request updateRequest(CreateReqRequest request, long id);
    RequestResponse findById(long id);
    Page<RequestResponse> getList(String search, Pageable pageable,String departmentId, int statusReq, String fromDate, String toDate);
    Page<RequestResponse> getListByUserId(String search, Pageable pageable, int statusReq, String fromDate, String toDate);
    void updateStatusRequest(long requestId, int statusRequest);
}
