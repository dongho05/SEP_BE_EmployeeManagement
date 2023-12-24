package com.project.SEP_BE_EmployeeManagement.service.impl;

import com.project.SEP_BE_EmployeeManagement.dto.request.holiday.HolidayRequest;
import com.project.SEP_BE_EmployeeManagement.dto.response.holiday.HolidayResponse;
import com.project.SEP_BE_EmployeeManagement.model.*;
import com.project.SEP_BE_EmployeeManagement.repository.AttendanceRepository;
import com.project.SEP_BE_EmployeeManagement.repository.HolidayRepository;
import com.project.SEP_BE_EmployeeManagement.repository.SignRepository;
import com.project.SEP_BE_EmployeeManagement.repository.UserRepository;
import com.project.SEP_BE_EmployeeManagement.service.AttendanceService;
import com.project.SEP_BE_EmployeeManagement.service.HolidayService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.accessibility.AccessibleText;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class HolidayServiceImpl implements HolidayService {
    @Autowired
    HolidayRepository holidayRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SignRepository signRepository;

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
    public Holiday createHoliday(HolidayRequest request) throws NotFoundException {
        Holiday obj = new Holiday();
        obj.setHolidayName(request.getHolidayName());
        obj.setStartDate(request.getStartDate());
        obj.setEndDate(request.getEndDate());
        holidayRepository.save(obj);

        // add holiday to attendance
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
                endDate = endDate.plusDays(1);
            }
            List<User> userList = userRepository.findAll();
            for (User u : userList) {
                Attendance attendance = attendanceRepository.findAttendanceByUserAndDate(u.getId(), currentDate);
                if(attendance == null){
                    attendance = new Attendance(u, currentDate);
                }
                attendance.setSigns(signRepository.findByName(ESign.L));
                attendanceRepository.save(attendance);
            }
            // Di chuyển đến ngày tiếp theo
            currentDate = currentDate.plusDays(1);
        }
        return obj;
    }

    @Override
    public Holiday updateHoliday(HolidayRequest request, int id) throws NotFoundException {
        if(!holidayRepository.existsById(id)){
            throw new RuntimeException("Ngày nghỉ không tồn tại");
        }

        Holiday obj = holidayRepository.findById(id);

        // delete holiday from attendance
        LocalDate startDate = obj.getStartDate();
        LocalDate endDate = obj.getEndDate();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            List<Attendance> attendanceList =  attendanceRepository.findAttendanceByDate(currentDate);
            for(Attendance i : attendanceList){
                i.setSigns(null);
            }
            // Di chuyển đến ngày tiếp theo
            currentDate = currentDate.plusDays(1);
        }

        obj.setHolidayName(request.getHolidayName());
        obj.setStartDate(request.getStartDate());
        obj.setEndDate(request.getEndDate());
        holidayRepository.save(obj);

        // add holiday to attendance
        startDate = request.getStartDate();
        endDate = request.getEndDate();
        currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            if(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY){
                endDate = endDate.plusDays(1);
            }
            List<User> userList = userRepository.findAll();
            for (User u : userList) {
                Attendance attendance = attendanceRepository.findAttendanceByUserAndDate(u.getId(), currentDate);
                if(attendance == null){
                    attendance = new Attendance(u, currentDate);
                }
                attendance.setSigns(signRepository.findByName(ESign.L));
                attendanceRepository.save(attendance);
            }
            // Di chuyển đến ngày tiếp theo
            currentDate = currentDate.plusDays(1);
        }

        return obj;
    }

    @Override
    public void deleteHoliday(int id) {
        Holiday obj = holidayRepository.findById(id);
        if(obj != null){
            // delete holiday from attendance
            LocalDate startDate = obj.getStartDate();
            LocalDate endDate = obj.getEndDate();
            LocalDate currentDate = startDate;
            while (!currentDate.isAfter(endDate)) {
                List<Attendance> attendanceList =  attendanceRepository.findAttendanceByDate(currentDate);
                for(Attendance i : attendanceList){
                    i.setSigns(null);
                }
                // Di chuyển đến ngày tiếp theo
                currentDate = currentDate.plusDays(1);
            }

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
