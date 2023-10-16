package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateRequestCategoryReq;
import com.project.SEP_BE_EmployeeManagement.model.Request;
import com.project.SEP_BE_EmployeeManagement.model.RequestCategory;
import com.project.SEP_BE_EmployeeManagement.service.RequestCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/request-category")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RequestCategoryController {
    @Autowired
    RequestCategoryService requestCategoryService;

    @GetMapping("/get-list-request-category")
    public ResponseEntity<?> getList(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "30") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<RequestCategory> pageRequests = requestCategoryService.getList(search, pageable);
        return ResponseEntity.ok(pageRequests);
    }
    @PostMapping("/create-request-category")
    public ResponseEntity<?> createRequestCategory(@RequestBody CreateRequestCategoryReq request){
        try {
            requestCategoryService.createRequestCategory(request);
        }catch (Exception e){

        }
        return ResponseEntity.ok("Thêm mới danh mục yêu cầu thành công.");
    }
    @DeleteMapping("/delete-request-category/{id}")
    public ResponseEntity<?> deleteRequestCategory(@PathVariable int id){
        try {
            requestCategoryService.deleteRequestCategory(id);
        }catch (Exception e){
            throw new RuntimeException("Đã có lỗi xảy ra khi xóa danh mục các yêu cầu.");
        }
        return ResponseEntity.ok("Xóa thành công.");
    }
    @PutMapping("/update-request-category/{id}")
    public ResponseEntity<?> updateRequestCategory(@RequestBody CreateRequestCategoryReq request, @PathVariable int id){
        try {
            requestCategoryService.updateRequestCategory(request,id);
        }catch (Exception e){

        }
        return ResponseEntity.ok("Cập nhật danh mục các yêu cầu thành công.");
    }

}
