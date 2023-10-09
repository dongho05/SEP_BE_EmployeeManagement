package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateRequestCategoryReq;
import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateRequestTypeReq;
import com.project.SEP_BE_EmployeeManagement.model.RequestCategory;
import com.project.SEP_BE_EmployeeManagement.model.RequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface RequestTypeService {
    RequestType createRequestType(CreateRequestTypeReq request);
    RequestType updateRequestType(CreateRequestTypeReq request, int id);
    RequestType findById(int id);
    Page<RequestType> getList(String search, Pageable pageable);
    void deleteRequestType(int id);
}
