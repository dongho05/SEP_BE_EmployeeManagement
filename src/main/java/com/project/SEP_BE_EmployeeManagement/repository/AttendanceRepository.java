package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.Attendance;
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
}
