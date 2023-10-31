package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.EWorkingTime;
import com.project.SEP_BE_EmployeeManagement.model.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Long> {
    @Query(value = "select w from WorkingTime w where w.workingTimeName = :workingTime")
    Optional<WorkingTime> findByWorkingTimeName(EWorkingTime workingTime);
}
