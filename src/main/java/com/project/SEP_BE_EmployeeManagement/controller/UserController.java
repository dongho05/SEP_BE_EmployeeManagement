package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.request.CreateUser;
import com.project.SEP_BE_EmployeeManagement.dto.response.MessageResponse;
import com.project.SEP_BE_EmployeeManagement.exportExcel.ExcelExportUser;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.repository.DepartmentRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @PostMapping(value = "create")

    public MessageResponse createUser(@RequestBody CreateUser createUser) {

//            for(CreateUser s : createUser){
//                userService.createUser(s);
//            }
        userService.createUser(createUser);
        return new MessageResponse("Thêm người dùng thành công");

    }

    @GetMapping(value = "data")
    public ResponseEntity<Page<User>> getData(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "30") int size,
                                              @RequestParam(name = "departmentId", required = false, defaultValue = "") String departmentId,
                                              @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                              @RequestParam(name = "status", required = false, defaultValue = "") String status)throws ParseException {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> pageUsers = userService.getData(departmentId,search,status, pageable);
        return new ResponseEntity<>(pageUsers, HttpStatus.OK);
    }

    @GetMapping("/export_users")
    public ResponseEntity exportUser(@RequestParam(name = "departmentId", required = false, defaultValue = "") String departmentId,
                                     @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                     @RequestParam(name = "status", required = false, defaultValue = "") String status,
                                     HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Danh sách nhân viên_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userRepository.getDataExport(departmentId,search,status);
        ExcelExportUser exportUser = new ExcelExportUser(listUsers,departmentRepository);
        exportUser.export(response);
//        if(id==0){
//            listUsers = userRepository.findAll();
//            ExcelExportUser exportUser = new ExcelExportUser(listUsers,departmentRepository);
//            exportUser.export(response);
//        }
//        else {
//            listUsers = userRepository.findAllByDepartment_Id(id);
//            ExcelExportUser exportUser = new ExcelExportUser(listUsers, id,departmentRepository);
//            exportUser.export(response);
//        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
