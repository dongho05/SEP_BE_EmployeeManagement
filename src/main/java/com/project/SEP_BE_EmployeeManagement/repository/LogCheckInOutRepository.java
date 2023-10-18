package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.LogCheckInOut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LogCheckInOutRepository extends JpaRepository<LogCheckInOut, Long> {

    @Query(value = "select l from LogCheckInOut l\n" +
            " join l.user u " +
            " where (:code is null or :code = '' or u.userCode = :code ) " +
            " and ( :departmentId = -1 or u.department.id = :departmentId ) " +
            " and (:search is null or :search = '' or u.fullName LIKE %:search% or u.department.name LIKE %:search% or u.username LIKE %:search%)" +
            " and l.dateCheck >= :from and l.dateCheck <= :to " +
            " order by l.dateCheck desc ")
    Page<LogCheckInOut> findByUserCodeAndDepartId(String code,Integer departmentId,String search,LocalDate from, LocalDate to, Pageable pageable);


    @Query(value = "select l from LogCheckInOut l\n" +
            " where l.dateCheck = ?1 order by l.timeCheck asc ")
    List<LogCheckInOut> findByDate(LocalDate date);

    @Query(value = "select l from LogCheckInOut l\n" +
            " where l.dateCheck = ?1 and l.timeCheck = ?2 and l.user.id = ?3 ")
    Optional<LogCheckInOut> checkExist(LocalDate date, LocalTime time, Long userId);
}
