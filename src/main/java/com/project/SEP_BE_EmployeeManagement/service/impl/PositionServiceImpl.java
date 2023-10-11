package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.PositionDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.department.CreateDepartmentRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.position.CreatePositionRequest;
import com.project.SEP_BE_EmployeeManagement.model.Position;
import com.project.SEP_BE_EmployeeManagement.model.Role;
import com.project.SEP_BE_EmployeeManagement.model.User;
import com.project.SEP_BE_EmployeeManagement.model.mapper.PositionMapper;
import com.project.SEP_BE_EmployeeManagement.repository.PositionRepository;
import com.project.SEP_BE_EmployeeManagement.repository.RoleRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.service.PositionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PositionServiceImpl implements PositionService {
    @Autowired
    PositionRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Position> GetAll() {
        return repository.findAll();
    }

    @Override
    public Page<PositionDto> getData(String search, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Position> page = repository.getPosition(search, pageable);

        Page<PositionDto> response = PositionMapper.toDtoPage(page);

        return response;
    }

    @Override
    public PositionDto getPositionById(long id) throws NotFoundException {
        PositionDto positionDto = PositionMapper.toDto(repository.findById(id).orElseThrow(() -> new NotFoundException("Position with id: " + id + " Not Found")));
        return positionDto;
    }

    @Override
    public Position createPosititon(CreatePositionRequest request) throws NotFoundException {
        Role role = roleRepository.findById(request.getRoleId()).orElseThrow(() -> new NotFoundException("Role with id: " + request.getRoleId() + " Not Found"));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        Position position = new Position();
        position.setPositionName(request.getPositionName());
        position.setRoles(roleSet);
        repository.save(position);
        return position;
    }

    @Override
    public Position updatePosition(CreatePositionRequest request, long id) throws NotFoundException {
        Position position = repository.findById(id).orElseThrow(() -> new NotFoundException("Position with id: " + id + " Not Found"));

        Role role = roleRepository.findById(request.getRoleId()).orElseThrow(() -> new NotFoundException("Role with id: " + request.getRoleId() + " Not Found"));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        position.setPositionName(request.getPositionName());
        position.setRoles(roleSet);
        repository.save(position);
        return position;
    }

    @Override
    public int deletePosition(long id) throws NotFoundException {
        Position position = repository.findById(id).orElseThrow(() -> new NotFoundException("Position with id: " + id + " Not Found"));
        List<User> userList = userRepository.getUserByPosition(id);
        if (userList.size() == 0) {
            repository.delete(position);
            return 1;
        }
        return 0;
    }

}
