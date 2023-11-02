package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.LogAttendanceHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAttendanceHistoryRepository extends JpaRepository<LogAttendanceHistory,Long> {
    @Query(value = "select * from log_attendance_history  " +
            " order by attendance_history_id",nativeQuery = true)
    Page<LogAttendanceHistory> getList( Pageable pageable);
}
