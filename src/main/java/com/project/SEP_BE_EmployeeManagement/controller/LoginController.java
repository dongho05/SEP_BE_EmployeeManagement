package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.request.LoginRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.login.PasswordRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.JwtResponse;
import com.project.SEP_BE_EmployeeManagement.extensions.Utilities;
import com.project.SEP_BE_EmployeeManagement.repository.RoleRepository;
import com.project.SEP_BE_EmployeeManagement.security.jwt.JwtUtils;
import com.project.SEP_BE_EmployeeManagement.security.jwt.UserDetailsImpl;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder encoder;
    private static Random generator = new Random();


    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);


        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @GetMapping("/api/get-all")
    public ResponseEntity<?> GetAllPerson() {
        return ResponseEntity.ok(userService.GetAllPerson());
    }

    @GetMapping("/api/get-user/{username}")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> GetPersonByUsername(String username) {
        return ResponseEntity.ok(userService.GetPersonByUsername(username));
    }

    
}
