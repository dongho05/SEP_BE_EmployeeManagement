package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateRequestCategoryReq;
import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateRequestTypeReq;
import com.project.SEP_BE_EmployeeManagement.model.RequestType;
import com.project.SEP_BE_EmployeeManagement.service.RequestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/request-type")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RequestTypeController {
    @Autowired
    RequestTypeService requestTypeService;


    @GetMapping("/get-list-request-type")
    public ResponseEntity<?> getList(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "30") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<RequestType> pageRequests = requestTypeService.getList(search, pageable);
        return ResponseEntity.ok(pageRequests);
    }
    @PostMapping("/create-request-type")
    public ResponseEntity<?> createRequestType(@RequestBody CreateRequestTypeReq request){
        try {
            requestTypeService.createRequestType(request);
        }catch (Exception e){

        }
        return ResponseEntity.ok("Thêm mới loại yêu cầu thành công.");
    }
    @DeleteMapping("/delete-request-type/{id}")
    public ResponseEntity<?> deleteRequestType(@PathVariable int id){
        try {
            requestTypeService.deleteRequestType(id);
        }catch (Exception e){
            throw new RuntimeException("Đã có lỗi xảy ra khi xóa loại yêu cầu.");
        }
        return ResponseEntity.ok("Xóa thành công.");
    }
    @PutMapping("/update-request-type/{id}")
    public ResponseEntity<?> updateRequestType(@RequestBody CreateRequestTypeReq request, @PathVariable int id){
        try {
            requestTypeService.updateRequestType(request,id);
        }catch (Exception e){

        }
        return ResponseEntity.ok("Cập nhật loại yêu cầu thành công.");
    }
}
