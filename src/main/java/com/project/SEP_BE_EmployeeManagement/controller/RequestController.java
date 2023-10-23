package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.request.request.CreateReqRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.request.UpdateStatusRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.request.RequestResponse;
import com.project.SEP_BE_EmployeeManagement.model.Request;
import com.project.SEP_BE_EmployeeManagement.security.jwt.UserDetailsImpl;
import com.project.SEP_BE_EmployeeManagement.service.RequestService;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/auth/request")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RequestController {
    @Autowired
    RequestService requestService;

    @Autowired
    UserService userService;

    @PostMapping("/create-request")
    public ResponseEntity<?> createRequest(@RequestBody CreateReqRequest request) {
        if (request.getStartDate().isAfter(request.getEndDate()) == true) {
            return ResponseEntity.internalServerError().body("Hãy chọn ngày bắt đầu nhỏ hơn ngày kết thúc.");
        }
        if (request.getStartTime().isAfter(request.getEndTime()) == true) {
            return ResponseEntity.internalServerError().body("Hãy chọn giờ bắt đầu nhỏ hơn giờ kết thúc.");
        }
        try {
            Request entity = requestService.createRequest(request);
            RequestResponse dto = new RequestResponse();
            dto.setId(entity.getId());
            dto.setRequestContent(entity.getRequestContent());
            dto.setRequestTitle(entity.getRequestTitle());
            dto.setCreatedBy(entity.getCreatedBy());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setEndDate(entity.getEndDate());
            dto.setEndTime(entity.getEndTime());
            dto.setRequestTypeId(entity.getRequestType().getId());
            dto.setStartDate(entity.getStartDate());
            dto.setStartTime(entity.getStartTime());
            dto.setUpdatedBy(entity.getUpdatedBy());
            dto.setUpdatedDate(entity.getUpdatedDate());
            dto.setUserId(entity.getUser().getId());


            return ResponseEntity.ok(dto);

        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

//        return ResponseEntity.ok("Thêm mới yêu cầu thành công.");
    }

    @GetMapping("/get-list-request")
    public ResponseEntity<?> getList(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "30") int size,
                                     @RequestParam(name = "status", defaultValue = "0") int statusReq,
                                     @RequestParam(name = "departmentId", defaultValue = "") String departmentId,
                                     @RequestParam(name = "from", defaultValue = "",required = false) String fromDate,
                                     @RequestParam(name = "to", defaultValue = "",required = false) String toDate) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RequestResponse> pageRequests = requestService.getList(search, pageable, departmentId,statusReq, fromDate, toDate);
        return ResponseEntity.ok(pageRequests);
    }

    @GetMapping("/get-list-request-by-user-id")
    public ResponseEntity<?> getListByUserId(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "30") int size,
                                     @RequestParam(name = "status", defaultValue = "0") int statusReq,
                                     @RequestParam(name = "from", defaultValue = "",required = false) String fromDate,
                                     @RequestParam(name = "to", defaultValue = "",required = false) String toDate) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RequestResponse> pageRequests = requestService.getListByUserId(search, pageable, statusReq, fromDate, toDate);
        return ResponseEntity.ok(pageRequests);
    }

    @PutMapping("/update-request/{id}")
    public ResponseEntity<?> updateRequest(@RequestBody CreateReqRequest request, @PathVariable int id) {

        if (request.getStartDate().isAfter(request.getEndDate()) == true) {
            return ResponseEntity.internalServerError().body("Hãy chọn ngày bắt đầu nhỏ hơn ngày kết thúc.");
        }
        if (request.getStartTime().isAfter(request.getEndTime()) == true) {
            return ResponseEntity.internalServerError().body("Hãy chọn giờ bắt đầu nhỏ hơn giờ kết thúc.");
        }

        Request obj = requestService.updateRequest(request, id);
        return ResponseEntity.ok("Cập nhật yêu cầu thành công.");
    }

    @GetMapping("/get-request-by-id/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return ResponseEntity.ok(requestService.findById(id));
    }

    @GetMapping("/get-current-user")
    public ResponseEntity<?> getCurrentUser() {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
//        String username = loggedInUser.getName();
        return ResponseEntity.ok(userDetails);
    }


    //Là ADMIN, duyệt trạng thái đơn cho thằng nhân viên

    @PostMapping("/update-status-request/{requestId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> updateSttRequest(@RequestBody UpdateStatusRequest statusRequest, @PathVariable long requestId) {
        try {
            requestService.updateStatusRequest(requestId, statusRequest.getStatus(),statusRequest.getNote());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.ok("");
    }
}
