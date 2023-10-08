package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateRequestReq;
import com.project.SEP_BE_EmployeeManagement.model.Request;
import com.project.SEP_BE_EmployeeManagement.security.jwt.UserDetailsImpl;
import com.project.SEP_BE_EmployeeManagement.service.RequestService;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RequestController {
    @Autowired
    RequestService requestService;

    @Autowired
    UserService userService;

    @PostMapping("/create-request")
    public ResponseEntity<?> createRequest(@RequestBody CreateRequestReq request){
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        request.setAcceptBy(userDetails.getId());

        requestService.createRequest(request);

        return ResponseEntity.ok("Thêm mới thành công");
    }
    @GetMapping("/get-list-request")
    public ResponseEntity<?> getList(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "30") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Request> pageRequests = requestService.getList(search, pageable);
        return ResponseEntity.ok(pageRequests);
    }
    @GetMapping("/get-current-user")
    public ResponseEntity<?> getCurrentUser(){
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
//        String username = loggedInUser.getName();
        return ResponseEntity.ok(userDetails);
    }

}
