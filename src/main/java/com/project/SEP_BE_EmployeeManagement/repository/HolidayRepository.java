package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.dto.response.holiday.HolidayResponse;
import com.project.SEP_BE_EmployeeManagement.model.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday,Integer> {
    @Query(value = "select * from holiday h " +
            " where (:search is null or :search = '' or h.holiday_name LIKE %:search% ) " +
            " and (:year is null or :year ='' or YEAR(h.start_date) = :year) order by h.start_date ",nativeQuery = true)
    Page<Holiday> getList(String search, Pageable pageable,Integer year);

    Boolean existsByHolidayName(String holidayName);
    Boolean existsById(int id);
    Holiday findById(int id);

    @Query(value = "select * from holiday h " +
            " order by h.start_date desc",nativeQuery = true)
    List<Holiday> getListByDateDesc();

    @Query(value = "SELECT * FROM holiday h \n" +
            "            where h.start_date <= ?1  and h.end_date >= ?1",nativeQuery = true)
    Holiday findExecuteAttendanceDate(String date);

    @Query("SELECT COUNT(h) > 0 FROM Holiday h WHERE :date BETWEEN h.startDate AND h.endDate")
    boolean isDateHoliday(LocalDate date);
}
