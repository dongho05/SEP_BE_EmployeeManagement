package com.project.SEP_BE_EmployeeManagement.service;

import com.project.SEP_BE_EmployeeManagement.dto.request.holiday.HolidayRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.holiday.HolidayResponse;
import com.project.SEP_BE_EmployeeManagement.model.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HolidayService {
    Page<HolidayResponse> getList(String search, Pageable pageable, Integer year);
    Holiday createHoliday(HolidayRequest request);
    Holiday updateHoliday(HolidayRequest holiday, int id);
    void deleteHoliday(int id);
    Holiday detailHoliday(int id);
    List<Integer> getListByDateDesc();
}
