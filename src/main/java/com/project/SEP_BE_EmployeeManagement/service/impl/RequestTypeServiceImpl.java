package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateReqTypeRequest;
import com.project.SEP_BE_EmployeeManagement.model.RequestType;
import com.project.SEP_BE_EmployeeManagement.repository.RequestTypeRepository;
import com.project.SEP_BE_EmployeeManagement.service.RequestCategoryService;
import com.project.SEP_BE_EmployeeManagement.service.RequestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RequestTypeServiceImpl implements RequestTypeService {
    @Autowired
    RequestTypeRepository requestTypeRepository;
    @Autowired
    RequestCategoryService requestCategoryService;
    @Override
    public RequestType createRequestType(CreateReqTypeRequest request) {
        RequestType obj = new RequestType();
        obj.setRequestTypeName(request.getRequestTypeName());
        obj.setReplacementPerson(request.getReplacementPerson());
        obj.setReplacementWork(request.getReplacementWork());
        obj.setRequestCategory(requestCategoryService.findById(request.getRequestCategoryId()));
        requestTypeRepository.save(obj);

        return obj;
    }

    @Override
    public RequestType updateRequestType(CreateReqTypeRequest request, int id) {
        RequestType obj = requestTypeRepository.findById(id);
        if(obj == null){
            throw new RuntimeException("Không tồn tại loại yêu cầu này.");
        }
        obj.setRequestCategory(requestCategoryService.findById(request.getRequestCategoryId()));
        obj.setRequestTypeName(request.getRequestTypeName());
        obj.setReplacementWork(request.getReplacementWork());
        obj.setReplacementPerson(request.getReplacementPerson());

        requestTypeRepository.save(obj);
        return obj;
    }

    @Override
    public RequestType findById(int id) {
        return requestTypeRepository.findById(id);
    }

    @Override
    public Page<RequestType> getList(String searchInput, Pageable pageable) {
        String search = searchInput == null || searchInput.toString() == "" ? "" : searchInput;
        Page<RequestType> list = requestTypeRepository.getList( search,pageable);
        return  list;
    }

    @Override
    public void deleteRequestType(int id) {
        RequestType obj = requestTypeRepository.findById(id);
        if(obj == null){
            throw new RuntimeException("Loại yêu cầu không tồn tại.");
        }
        requestTypeRepository.delete(obj);
    }
}
