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
import org.springframework.stereotype.Component;

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
    public List<UserResponse> GetAllPerson() {
        List<UserResponse> response = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User u:
             list) {
            UserResponse ur = new UserResponse();
            ur.setAddress(u.getAddress());
            ur.setEmail(u.getEmail());
            ur.setPassword(u.getPassword());
            ur.setPhone(u.getPhone());
            ur.setUserImage(u.getUserImage());
            ur.setStatus(u.isStatus());
            ur.setBirthDay(u.getBirthDay());
            ur.setStartDate(u.getStartWork());
            ur.setEndDate(u.getEndWork());
            ur.setFullName(u.getFullName());
            ur.setGender(u.isGender());

            response.add(ur);
        }
        return response;
    }

    @Override
    public Optional<User> GetPersonByUsername(String username) {
        return userRepository.findByUsername(username);
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
        if (userRepository.existsByUserCode(createUser.getUserCode())) {
            throw new RuntimeException("Mã nhân viên đã tồn tại!");
        }
        user.setUserCode(createUser.getUserCode());

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
        user.setGender(createUser.isGender());
        user.setStartWork(createUser.getStartWork());
        user.setEndWork(createUser.getEndWork());
        user.setBirthDay(createUser.getBirthDay());
        user.setPhone(createUser.getPhone());
        user.setAddress(createUser.getAddress());
        user.setEmail(createUser.getEmail());
        user.setStatus(true);

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
}
