package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateReqRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.request.RequestResponse;
import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import com.project.SEP_BE_EmployeeManagement.model.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface RequestService {

    Request createRequest(CreateReqRequest request);
    Request updateRequest(CreateReqRequest request, long id);
    RequestResponse findById(long id);
    Page<RequestResponse> getList(String search, String departmentId, String statusReq, String fromDate, String toDate, Pageable pageable);
    Page<RequestResponse> getListByUserId(String searchInput, String statusReq, String fromDate, String toDate, Pageable pageable);
    void updateStatusRequest(long requestId, int statusRequest,String note);
    List<Request> processRequestOnMonth(Integer day, Integer month, Integer year);
//    List<Request> processRequestOnMonth();
}
