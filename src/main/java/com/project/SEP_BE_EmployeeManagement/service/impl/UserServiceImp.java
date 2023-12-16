package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.UserDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.CreateUser;
import com.project.SEP_BE_EmployeeManagement.dto.request.LoginRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.User.UpdateUserRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.User.ProfileRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.MessageResponse;
import com.project.SEP_BE_EmployeeManagement.model.Contract;
import com.project.SEP_BE_EmployeeManagement.model.Department;
import com.project.SEP_BE_EmployeeManagement.model.Position;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.model.mapper.UserMapper;
import com.project.SEP_BE_EmployeeManagement.repository.ContractRepository;
import com.project.SEP_BE_EmployeeManagement.repository.DepartmentRepository;
import com.project.SEP_BE_EmployeeManagement.repository.PositionRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    FileManagerService fileManagerService;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    ContractRepository contractRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
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
        return userRepository.findByUsernameOrEmail(username);
    }

    @Override
    public UserDto getUserById(long id) throws NotFoundException {
        return UserMapper.toUserDto(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " Not Found"))) ;
    }

    @Override
    public UserDto updatePosition(long userId, long positionId) throws NotFoundException {
        User u = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " Not Found"));
        Position p = positionRepository.findById(positionId).orElseThrow(() -> new NotFoundException("Position with id: " + positionId + " Not Found"));
        u.setPosition(p);
        userRepository.save(u);
        return UserMapper.toUserDto(u);
    }

    @Override
    public UserDto updateUser(long id, UpdateUserRequest updateUserRequest) throws NotFoundException {
        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " Not Found"));
        u.setFullName(updateUserRequest.getFullName());
        u.setUserCode("FPT_"+updateUserRequest.getUserCode());
        u.setGender(updateUserRequest.getGender());
        u.setAddress(updateUserRequest.getAddress());
        u.setEmail(updateUserRequest.getEmail());
        u.setPhone(updateUserRequest.getPhone());
        u.setBirthDay(updateUserRequest.getBirthDay());

        // set department
        Optional<Department> department = departmentRepository.findById(updateUserRequest.getDepartmentId());
        if(!department.isPresent()){
            throw new RuntimeException("Chưa chọn phòng ban!");
        }
        u.setDepartment(department.get());

        // set position
        Optional<Position> position = positionRepository.findById(updateUserRequest.getPositionId());
        if(!position.isPresent()){
            throw new RuntimeException("Chưa chọn vị trí!");
        }
        u.setPosition(position.get());

        if (updateUserRequest.getUserImage() != null && updateUserRequest.getUserImage().getSize() > 0) {
            if(!u.getUserImage().equals("default.png"))
                fileManagerService.delete(u.getUserImage());
            String filename = fileManagerService.saveUserImage(updateUserRequest.getUserImage());
            u.setUserImage(filename);
        }
        // set user Image
//        String userImage = fileManagerService.saveUserImage(updateUserRequest.getUserImage());
//        u.setUserImage(userImage);
        userRepository.save(u);



        //set contracts
        if(updateUserRequest.getContractFile() != null && updateUserRequest.getContractFile().getSize()  >0){
            Set<Contract> contracts = new HashSet<>();
            Contract contract = new Contract();
            String contractFile = fileManagerService.saveUserContract(updateUserRequest.getContractFile());
            contract.setFileName(contractFile);
            contract.setUser(u);
//        contract.setContractName(createUser.getContractName());
            contract.setContractName("Hợp đồng");
            contractRepository.save(contract);

            contracts.add(contract);
            u.setContracts(contracts);
            userRepository.save(u);
        }


        //set contracts
//        Set<Contract> contracts = new HashSet<>();
//        Contract contract = new Contract();
//        String contractFile = fileManagerService.saveUserContract(updateUserRequest.getContractFile());
//        contract.setFileName(contractFile);
//        u.setContracts(contracts);

        return UserMapper.toUserDto(userRepository.save(u));
    }

    @Override
    public MessageResponse changeStatus(long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " Not Found"));
        String mess = "";
        if(user.getStatus()==1) {
            user.setStatus(0);
            mess = "Vô hiệu hoá mã nhân viên "+user.getUserCode();
        }
        else if(user.getStatus()==0){
            user.setStatus(1);
            mess = "Mở mã nhân viên "+user.getUserCode();
        }
        userRepository.save(user);
        return new MessageResponse(mess);
//        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateProfile(ProfileRequest profileRequest, long id) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " Not Found"));
        user.setFullName(profileRequest.getFullName());
        user.setPhone(profileRequest.getPhone());
        user.setAddress(profileRequest.getAddress());
        user.setGender(profileRequest.getGender());
        user.setBirthDay(profileRequest.getBirthDay());

        return UserMapper.toUserDto(userRepository.save(user));
    }

    public Optional<User> findByUsernameOrEmail(String email) {
        return userRepository.findByUsernameOrEmail(email);
    }


    @Override
    public User createUser(CreateUser createUser) {
        User user = new User();
        String password = "123";
        // set user name
        if (userRepository.existsByUsername(createUser.getUsername())) {
            throw new RuntimeException("Tài khoản đã tồn tại!");
        }
        user.setUsername(createUser.getUsername().trim());

        // set password
        user.setPassword(encoder.encode(password.trim()));
//        user.setPassword(encoder.encode(createUser.getPassword()));

        String userCode = createUser.getUserCode().trim();
        int length = userCode.length();
        if (length == 1) userCode = "FPT_0000" + createUser.getUserCode().trim();
        if (length == 2) userCode = "FPT_000" + createUser.getUserCode().trim();
        if (length == 3) userCode = "FPT_00" + createUser.getUserCode().trim();
        if (length == 4) userCode = "FPT_0" + createUser.getUserCode().trim();
        if (length == 5) userCode = "FPT_" + createUser.getUserCode().trim();
        // set user code
        if (userRepository.existsByUserCode(createUser.getUserCode().trim())) {
            throw new RuntimeException("Mã nhân viên đã tồn tại!");
//            return new ResponseEntity<>("Mã nhân viên đã tồn tại!", HttpStatus.OK);
        }
//        user.setUserCode("FPT_"+createUser.getUserCode());
        user.setUserCode(userCode);

        if (userRepository.existsByEmail(createUser.getEmail().trim())) {
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
        user.setFullName(createUser.getFullName().trim());
        user.setGender(createUser.getGender());
        user.setStartWork(createUser.getStartWork());
        user.setEndWork(createUser.getEndWork());
        user.setBirthDay(createUser.getBirthDay());
        user.setPhone(createUser.getPhone().trim());
        user.setAddress(createUser.getAddress().trim());
        user.setStatus(1);
        userRepository.save(user);

        //set contracts
        if(createUser.getContractFile() != null){
            Set<Contract> contracts = new HashSet<>();
            Contract contract = new Contract();
            String contractFile = fileManagerService.saveUserContract(createUser.getContractFile());
            contract.setFileName(contractFile);
            contract.setUser(user);
//        contract.setContractName(createUser.getContractName());
            contract.setContractName("Hợp đồng");
            contractRepository.save(contract);

            contracts.add(contract);
            user.setContracts(contracts);
        }

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

//    @Override
//    public void UpdatePassword(String email, String newPassword) {
//        userRepository.UpdatePassword(email,newPassword);
//    }

    @Override
    public Boolean existsByEmail(String email) {
        boolean result = userRepository.existsByEmail(email);
        if(result == true){
            return true;
        }
        return false;
    }

    @Override
    public Double getDayOffByUserId(Long id) {
        return userRepository.getDayOffByUserId(id).orElseThrow();
    }


    private static String alphaNumericString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}
