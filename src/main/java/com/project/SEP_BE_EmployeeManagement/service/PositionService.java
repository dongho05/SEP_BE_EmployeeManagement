package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.DepartmentDto;
import com.project.SEP_BE_EmployeeManagement.dto.PositionDto;
import com.project.SEP_BE_EmployeeManagement.dto.request.department.CreateDepartmentRequest;
import com.project.SEP_BE_EmployeeManagement.dto.request.position.CreatePositionRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.department.DepartmentResponse;
import com.project.SEP_BE_EmployeeManagement.model.Position;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PositionService {
    List<Position> GetAll();

    Page<PositionDto> getData(String search, Integer pageNo, Integer pageSize);
    Position createPosititon(CreatePositionRequest request) throws NotFoundException;

    Position updatePosition(CreatePositionRequest request, long id) throws NotFoundException;

    int deletePosition(long id) throws NotFoundException;
}
