package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query(value = "select * from attendance l \n" +
            "join users u on l.user_id = u.id \n" +
            "where u.user_code = ?1 \n" +
            "and l.date_log = ?2", nativeQuery = true)
    Attendance findByUserCodeAndDate(String code, LocalDate date);

    @Query(value = "select a from Attendance a\n" +
            "where a.dateLog = ?1 ")
    List<Attendance> findByDate(LocalDate date);

    @Query(value = "select a from Attendance a\n" +
            " join a.user u " +
            "where u.userCode= ?1 " +
            "and YEAR (a.dateLog) = ?2  ")
    List<Attendance> findByUserCodeAndMonthAndYear(String code,Integer year);

    @Query(value = "select l from Attendance l\n" +
            " join l.user u " +
            "where u.department.id= ?1 and MONTH (l.dateLog) = ?2 and YEAR (l.dateLog) = ?3 and u.fullName LIKE %?4%  ")
    List<Attendance> findByMonthAndDepartment(Long id, Integer month,Integer year,String search);

    @Query(value = "select l from Attendance l\n" +
            " join l.user u " +
            "where MONTH (l.dateLog) = ?1 and YEAR (l.dateLog) = ?2 and u.fullName LIKE %?3% ")
    List<Attendance> findByMonth(Integer month,Integer year, String search);
}
