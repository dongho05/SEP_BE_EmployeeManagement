package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateRequestCategoryReq;
import com.project.SEP_BE_EmployeeManagement.model.Request;
import com.project.SEP_BE_EmployeeManagement.model.RequestCategory;
import com.project.SEP_BE_EmployeeManagement.repository.RequestCategoryRepository;
import com.project.SEP_BE_EmployeeManagement.service.RequestCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RequestCategoryServiceImpl implements RequestCategoryService {
    @Autowired
    RequestCategoryRepository requestCategoryRepository;
    @Override
    public RequestCategory createRequestCategory(CreateRequestCategoryReq request) {
        RequestCategory obj = new RequestCategory();
        obj.setRequestCategoryName(request.getRequestCategoryName());
        requestCategoryRepository.save(obj);
        return obj;
    }

    @Override
    public RequestCategory updateRequestCategory(CreateRequestCategoryReq request, int id) {
        RequestCategory rc = requestCategoryRepository.findById(id);
        if(rc != null){
            rc.setRequestCategoryName(request.getRequestCategoryName());
            requestCategoryRepository.save(rc);
        }
        return null;
    }

    @Override
    public RequestCategory findById(int id) {
        return requestCategoryRepository.findById(id);
    }

    @Override
    public Page<RequestCategory> getList(String searchInput, Pageable pageable) {
        String search = searchInput == null || searchInput.toString() == "" ? "" : searchInput;
        Page<RequestCategory> list = requestCategoryRepository.getList( search,pageable);
        return  list;
    }

    @Override
    public void deleteRequestCategory(int id) {
        RequestCategory rc = requestCategoryRepository.findById(id);
        if(rc == null) {
            throw  new RuntimeException("Không tìm thấy danh mục yêu cầu cần xóa.");
        }
        requestCategoryRepository.delete(rc);
    }
}
