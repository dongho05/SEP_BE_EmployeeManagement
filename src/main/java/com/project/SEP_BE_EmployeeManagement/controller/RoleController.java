package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.model.Role;
import com.project.SEP_BE_EmployeeManagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/data")
    public ResponseEntity<Page<Role>> getRole(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
                                              @RequestParam(name = "pageSize", defaultValue = "30") int pageSize,
                                              @RequestParam(name = "search", required = false, defaultValue = "") String search) {
        Page<Role> pageRoles = roleService.getRole(search, pageNo, pageSize);
        return new ResponseEntity<>(pageRoles, HttpStatus.OK);
    }
}
