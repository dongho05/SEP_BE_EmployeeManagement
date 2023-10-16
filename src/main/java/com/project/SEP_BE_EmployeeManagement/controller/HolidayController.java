package com.project.SEP_BE_EmployeeManagement.controller;

import com.project.SEP_BE_EmployeeManagement.dto.request.holiday.HolidayReq;
import com.project.SEP_BE_EmployeeManagement.dto.response.holiday.HolidayResponse;
import com.project.SEP_BE_EmployeeManagement.model.Holiday;
import com.project.SEP_BE_EmployeeManagement.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/holiday")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HolidayController {
    @Autowired
    HolidayService holidayService;

    @PostMapping("/create-holiday")
    public ResponseEntity<?> createHoliday(@RequestBody HolidayReq request) {
        holidayService.createHoliday(request);
        return ResponseEntity.ok("Tạo mới thành công.");
    }

    @GetMapping("/get-all-holiday")
    public ResponseEntity<?> getList(@RequestParam(name = "search", required = false, defaultValue = "") String search,
                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "30") int size,
                                     @RequestParam(name = "year", required = false,defaultValue = "") Integer year) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HolidayResponse> pageHolidays = holidayService.getList(search, pageable,year);
        return ResponseEntity.ok(pageHolidays);
    }

    @PutMapping("/update-holiday/{id}")
    public ResponseEntity<?> updateHoliday(@RequestBody HolidayReq request, @PathVariable int id) {
        if (request.getStartDate().isAfter(request.getEndDate()) == true) {
            throw new RuntimeException("Hãy chọn ngày bắt đầu nhở hơn ngày kết thúc.");
        }
        try {
            holidayService.updateHoliday(request, id);
        } catch (Exception e) {

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-holiday/{id}")
    public ResponseEntity<?> deleteHoliday(@PathVariable int id) {
        try {
            holidayService.deleteHoliday(id);

        } catch (Exception e) {

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/holiday-details/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        Holiday obj= holidayService.detailHoliday(id);
        return ResponseEntity.ok(obj);
    }
    @GetMapping("/get-holidays/get-years")
    public ResponseEntity<?> getYearFromHolidays(){
        return ResponseEntity.ok(holidayService.getListByDateDesc());
    }
}
