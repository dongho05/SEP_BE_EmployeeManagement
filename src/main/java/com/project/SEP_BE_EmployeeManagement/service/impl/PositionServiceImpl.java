package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.model.Position;
import com.project.SEP_BE_EmployeeManagement.repository.PositionRepository;
import com.project.SEP_BE_EmployeeManagement.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PositionServiceImpl implements PositionService {
    @Autowired
    PositionRepository repository;
    @Override
    public List<Position> GetAll() {
        return repository.findAll();
    }
}
