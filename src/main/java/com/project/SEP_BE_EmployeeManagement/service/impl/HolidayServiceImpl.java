package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.request.holiday.HolidayRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.holiday.HolidayResponse;
import com.project.SEP_BE_EmployeeManagement.model.Holiday;
import com.project.SEP_BE_EmployeeManagement.repository.HolidayRepository;
import com.project.SEP_BE_EmployeeManagement.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class HolidayServiceImpl implements HolidayService {
    @Autowired
    HolidayRepository holidayRepository;
    @Override
    public Page<HolidayResponse> getList(String searchInput, Pageable pageable,Integer year) {
        String search = searchInput == null || searchInput.toString() == "" ? "" : searchInput;
        Page<Holiday> list = holidayRepository.getList(search,pageable,year);
        Page<HolidayResponse> result = list.map(new Function<Holiday, HolidayResponse>() {
            @Override
            public HolidayResponse apply(Holiday entity) {

                long totalDays = DAYS.between(entity.getStartDate(), entity.getEndDate());

                HolidayResponse dto = new HolidayResponse();
                // Conversion logic
                dto.setId(entity.getId());
                dto.setHolidayName(entity.getHolidayName());
                dto.setStartDate(entity.getStartDate());
                dto.setEndDate(entity.getEndDate());
                dto.setTotalDayOff((int)totalDays + 1);
                return dto;
            }
        });
        return result;
    }

    @Override
    public Holiday createHoliday(HolidayRequest request) {
        Holiday obj = new Holiday();
        obj.setHolidayName(request.getHolidayName());
        obj.setStartDate(request.getStartDate());
        obj.setEndDate(request.getEndDate());
        holidayRepository.save(obj);
        return obj;
    }

    @Override
    public Holiday updateHoliday(HolidayRequest request, int id) {
        if(!holidayRepository.existsById(id)){
            throw new RuntimeException("Ngày nghỉ không tồn tại");
        }
        holidayRepository.updateHoliday(request.getHolidayName(), request.getStartDate(),request.getEndDate(),id);
        Holiday obj = holidayRepository.findById(id);
        return obj;
    }

    @Override
    public void deleteHoliday(int id) {
        Holiday obj = holidayRepository.findById(id);
        if(obj != null){
            holidayRepository.delete(obj);
        }
    }

    @Override
    public Holiday detailHoliday(int id) {
       Holiday obj =holidayRepository.findById(id);
       if(obj == null){
           throw new RuntimeException("Không có ngày nghỉ phù hợp.");
       }
       return obj;
    }

    @Override
    public List<Integer> getListByDateDesc() {
        List<Holiday> holidays = holidayRepository.getListByDateDesc();
        List<Integer> holidayYears = new ArrayList<>();
        int smallestYear = holidays.get(holidays.size()-1).getStartDate().getYear() - 1;
        int largestYear = holidays.get(0).getStartDate().getYear() + 1;
        for (int i = smallestYear;i<= largestYear; i++){
            holidayYears.add(i);
        }
        return holidayYears;
    }

    @Override
    public boolean isDateHoliday(LocalDate date) {
        return holidayRepository.isDateHoliday(date);
    }
}
