package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.model.Position;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PositionService {
    List<Position> GetAll();
}
