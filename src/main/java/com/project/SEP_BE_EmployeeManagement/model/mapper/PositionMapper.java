package com.project.SEP_BE_EmployeeManagement.model.mapper;


import com.project.SEP_BE_EmployeeManagement.dto.PositionDto;
import com.project.SEP_BE_EmployeeManagement.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

public class PositionMapper {

    public static PositionDto toDto(Position position) {
        PositionDto dto = new PositionDto();
        dto.setId(position.getId());
        dto.setName(position.getPositionName());
        dto.setCreatedDate(position.getCreatedDate());
        dto.setUpdatedDate(position.getUpdatedDate());
        return dto;
    }

    public static List<PositionDto> toDtoList(List<Position> positiontList) {
        return positiontList.stream().map(PositionMapper::toDto).collect(Collectors.toList());
    }

    public static Page<PositionDto> toDtoPage(Page<Position> departmentPage) {
        return new PageImpl<>(
                departmentPage.getContent().stream()
                        .map(PositionMapper::toDto)
                        .collect(Collectors.toList()),
                PageRequest.of(departmentPage.getPageable().getPageNumber(), departmentPage.getPageable().getPageSize()),
                departmentPage.getTotalElements()
        );
    }
}
