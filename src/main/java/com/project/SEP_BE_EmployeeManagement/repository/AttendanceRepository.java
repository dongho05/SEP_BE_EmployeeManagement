package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @Query("SELECT a FROM Attendance a " +
            "WHERE a.user.id = :userId " +
            "AND YEAR(a.dateLog) = :year " +
            "AND MONTH(a.dateLog) = :month")
    List<Attendance> findAttendancesForUserInMonth(Long userId, int year, int month);

    @Query(value = "select a from attendance a " +
            "inner join users u on a.user_id = u.id where" +
            " (:departmentId is null or :departmentId ='' or u.department_id = :departmentId) " +
            " and (:userId is null or :userId ='' or a.user_id = :userId) " +
            " and (:fromDate is null or :fromDate='' or a.date_log >= :fromDate) " +
            " and (:toDate is null or :toDate='' or a.date_log <= :toDate) " +
            " order by a.attendance_id",nativeQuery = true)
    Page<Attendance> getList(String departmentId, String userId, String fromDate, String toDate, Pageable pageable);
}
