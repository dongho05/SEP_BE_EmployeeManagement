package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.request.holiday.HolidayRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.holiday.HolidayResponse;
import com.project.SEP_BE_EmployeeManagement.model.Holiday;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface HolidayService {
    Page<HolidayResponse> getList(String search, Pageable pageable, Integer year);
    Holiday createHoliday(HolidayRequest request) throws NotFoundException;
    Holiday updateHoliday(HolidayRequest holiday, int id) throws NotFoundException;
    void deleteHoliday(int id);
    Holiday detailHoliday(int id);
    List<Integer> getListByDateDesc();
    boolean isDateHoliday(LocalDate date);
}
