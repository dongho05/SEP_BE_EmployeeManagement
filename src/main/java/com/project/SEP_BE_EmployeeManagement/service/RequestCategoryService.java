package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateReqCategoryRequest;
import com.project.SEP_BE_EmployeeManagement.model.RequestCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface RequestCategoryService {
    RequestCategory createRequestCategory(CreateReqCategoryRequest request);
    RequestCategory updateRequestCategory(CreateReqCategoryRequest request, int id);
    RequestCategory findById(int id);
    Page<RequestCategory> getList(String search, Pageable pageable);
    void deleteRequestCategory(int id);
}
