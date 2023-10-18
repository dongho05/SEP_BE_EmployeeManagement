package com.project.SEP_BE_EmployeeManagement.repository;


import com.project.SEP_BE_EmployeeManagement.model.LogInLateOutEarly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface LogInLateOutEarlyRepository extends JpaRepository<LogInLateOutEarly, Long> {
    @Query(value = "select l from LogInLateOutEarly l\n" +
            " join l.user u " +
            " where (:code is null or :code = '' or u.userCode = :code ) " +
            " and ( :departmentId = -1 or u.department.id = :departmentId ) " +
            " and (:search is null or :search = '' or u.fullName LIKE %:search% or u.department.name LIKE %:search% or u.username LIKE %:search%)" +
            " and l.dateCheck >= :from and l.dateCheck <= :to " +
            " order by l.dateCheck desc ")
    Page<LogInLateOutEarly> getData(String code, Integer departmentId, String search, LocalDate from, LocalDate to, Pageable pageable);

    @Query(value = "select l from LogInLateOutEarly l\n" +
            " where l.dateCheck = ?1 and (l.timeEnd = ?2 or l.timeStart = ?2) and l.user.id = ?3 ")
    Optional<LogInLateOutEarly> checkExist(LocalDate date, LocalTime time, Long userId);
}
