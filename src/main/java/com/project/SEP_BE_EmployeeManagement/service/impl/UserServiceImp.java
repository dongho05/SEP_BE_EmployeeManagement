package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.request.CreateUser;
import com.project.SEP_BE_EmployeeManagement.dto.request.LoginRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.User.UserRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.user.UserResponse;
import com.project.SEP_BE_EmployeeManagement.model.Contract;
import com.project.SEP_BE_EmployeeManagement.model.Department;
import com.project.SEP_BE_EmployeeManagement.model.Position;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.model.mapper.UserMapper;
import com.project.SEP_BE_EmployeeManagement.repository.DepartmentRepository;
import com.project.SEP_BE_EmployeeManagement.repository.PositionRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    FileManagerService fileManagerService;
    @Autowired
    PositionRepository positionRepository;
    @Override
    public User login(LoginRequest request) {
        User user = userRepository.findByUsernameAndPassword(request.getUsername(),request.getPassword());
        if(user != null){
            return user;
        }
        return null;
    }



    @Override
    public Optional<User> GetPersonByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User GetPersonByEmail(String email) {
        return null;
    }

    @Override
    public User CreateNewPerson(User user) {
        User u = userRepository.save(user);
        return u;
    }

    @Override
    public User UpdatePerson(UserRequest user) {
        User u = UserMapper.toUser(user);
        return null;
    }

    @Override
    public User createUser(CreateUser createUser) {
        User user = new User();
        // set user name
        if (userRepository.existsByUsername(createUser.getUsername())) {
            throw new RuntimeException("Tài khoản đã tồn tại!");
        }
        user.setUsername(createUser.getUsername());

        // set password
        user.setPassword(createUser.getPassword());

        // set user code
        if (userRepository.existsByEmail(createUser.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }
        user.setEmail(createUser.getEmail());
        user.setUserCode(createUser.getUserCode());

        if (userRepository.existsByEmail(createUser.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }
        user.setEmail(createUser.getEmail());

        // set department
        Optional<Department> department = departmentRepository.findById(createUser.getDepartmentId());
        if(!department.isPresent()){
            throw new RuntimeException("Chưa chọn phòng ban!");
        }
        user.setDepartment(department.get());

        // set user Image
        String userImage = fileManagerService.saveUserImage(createUser.getUserImage());
        user.setUserImage(userImage);

        // set position
        Optional<Position> position = positionRepository.findById(createUser.getPositionId());
        if(!position.isPresent()){
            throw new RuntimeException("Chưa chọn vị trí!");
        }
        user.setPosition(position.get());

        // common
        user.setFullName(createUser.getFullName());
        user.setGender(createUser.getGender());
        user.setStartWork(createUser.getStartWork());
        user.setEndWork(createUser.getEndWork());
        user.setBirthDay(createUser.getBirthDay());
        user.setPhone(createUser.getPhone());
        user.setAddress(createUser.getAddress());
        user.setStatus(1);

        //set contracts
        Set<Contract> contracts = new HashSet<>();
        Contract contract = new Contract();
//        String contractFile = fileManagerService.saveUserContract(createUser.getContractFile());
//        contract.setFileName(contractFile);

        contract.setContractName(createUser.getContractName());
        user.setContracts(contracts);
        userRepository.save(user);
        return user;
    }

    @Override
    public Page<User> getData( String departmentIdInput, String searchInput, String statusInput, Pageable pageable) {

//        Integer departId = departmentIdInput == null || departmentIdInput == "" ? -1 : Integer.parseInt(departmentIdInput);
//        String search = searchInput == null || searchInput.toString() == "" ? "" : searchInput;
//        Integer status = statusInput == null || statusInput.toString() == "" ? -1 : Integer.parseInt(statusInput);
        String departId = departmentIdInput == null || departmentIdInput == "" ? "" : departmentIdInput;
        String search = searchInput == null || searchInput.toString() == "" ? "" : searchInput;
        String status = statusInput == null || statusInput.toString() == "" ? "" : statusInput;

        Page<User> list = userRepository.getData( departId,search, status,pageable);
//        Page<User> list = userRepository.getData(departId,pageable);
        return list;
    }

    @Override
    public boolean UpdatePassword(String email, String newPassword) {
        return false;
    }
}
