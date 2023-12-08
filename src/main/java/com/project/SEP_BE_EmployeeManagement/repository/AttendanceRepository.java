package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a " +
            "WHERE a.user.id = :userId " +
            "AND YEAR(a.dateLog) = :year " +
            "AND MONTH(a.dateLog) = :month")
    List<Attendance> findAttendancesForUserInMonth(Long userId, int year, int month);

    @Query(value = "select * from attendance a " +
            "inner join users u on a.user_id = u.id where" +
            " (:departmentId is null or :departmentId ='' or u.department_id = :departmentId) " +
            " and (:userId is null or :userId ='' or a.user_id = :userId) " +
            " and (:fromDate is null or :fromDate='' or a.date_log >= :fromDate) " +
            " and (:toDate is null or :toDate='' or a.date_log <= :toDate) " +
            " order by a.attendance_id",nativeQuery = true)
    Page<Attendance> getList(String departmentId, String userId, String fromDate, String toDate, Pageable pageable);

    @Query("SELECT a FROM Attendance a WHERE a.user.id = :userId AND a.dateLog = :targetDate")
    Attendance findAttendanceByUserAndDate(Long userId, LocalDate targetDate);


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

    @Query(value = "select l from Attendance l\n" +
            " join l.user u " +
            "where  MONTH (l.dateLog) = ?1 and YEAR (l.dateLog) = ?2 " +
            "order by l.dateLog asc ")
    List<Attendance> findByMonthSortDate(Integer month, Integer year);

    @Query(value = "select l from Attendance l\n" +
            " join l.user u " +
            "where u.department.id= ?1 and MONTH (l.dateLog) = ?2 and YEAR (l.dateLog) = ?3 " +
            "order by l.dateLog asc ")
    List<Attendance> findByMonthAndDepartmentSortDate(Long id, Integer month,Integer year);
}
