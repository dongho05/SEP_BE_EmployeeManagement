package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.request.LoginRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.login.PasswordRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.login.ResetPasswordRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.login.UpdatePasswordRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.mail.MailRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.JwtResponse;
import com.project.SEP_BE_EmployeeManagement.extensions.Utilities;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.repository.RoleRepository;
import com.project.SEP_BE_EmployeeManagement.security.jwt.JwtUtils;
import com.project.SEP_BE_EmployeeManagement.security.jwt.UserDetailsImpl;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import com.project.SEP_BE_EmployeeManagement.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
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
    private MailService mailService;

    public LoginController(MailService mailService) {
        this.mailService = mailService;
    }
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

        Optional<User> user = userService.findByUsernameOrEmail(loginRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                user.get().getUserCode(),
                user.get().getDepartment().getId(),
                user.get().getFullName()));
    }



    @GetMapping("/api/get-user/{username}")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> GetPersonByUsername(String username) {
        return ResponseEntity.ok(userService.GetPersonByUsername(username));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> ForgotPassword(@RequestBody PasswordRequest request) {
        int length = 8 + generator.nextInt(12);
        String randomPassword = Utilities.alphaNumericString(length);
        String hashedPassword = encoder.encode(randomPassword);

        mailService.sendPassword(new ResetPasswordRequest(request.getEmail(),
                "Reset password",
                "This is new password: " + randomPassword + ". \nLogin with this password, and change password"));
        try {
            userService.UpdatePassword(request.getEmail(), hashedPassword);
        }catch (Exception e){
        }
        return ResponseEntity.ok(hashedPassword);
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> ChangePassword(@RequestBody UpdatePasswordRequest request){

        Optional<User> user = userService.findByUsernameOrEmail(request.getEmail());
        if(!encoder.matches(request.getOldPassword(), user.get().getPassword())){
            throw new RuntimeException("Mật khẩu cũ không đúng!");
        }
        if(!request.getNewPassword1().equals(request.getNewPassword2())){
            throw new RuntimeException("Mật khẩu nhập lại không đúng!");
        }
        String hashPassword = encoder.encode(request.getNewPassword1());
        try {
            userService.UpdatePassword(request.getEmail(), hashPassword);

        }catch (Exception e){

        }
        return ResponseEntity.ok(request.getNewPassword1());
    }
}
