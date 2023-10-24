package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {
    @Query(value = "select r.*,u.department_id from request r " +
            " join users u on r.user_id = u.id " +
            " where (:search is null or :search = '' or r.request_title LIKE %:search% ) " +
            " and (:departmentId is null or :departmentId ='' or u.department_id = :departmentId) " +
            " and (:statusReq is null or :statusReq = 0 or r.status = :statusReq) "+
            " and (:userId is null or :userId ='' or r.user_id = :userId) " +
            " and (:fromDate is null or :fromDate='' or r.create_date >= :fromDate) " +
            " and (:toDate is null or :toDate='' or r.create_date <= :toDate) " +
            " order by r.request_id",nativeQuery = true)
    Page<Request> getList(String search, String departmentId, int statusReq, Long userId, String fromDate, String toDate, Pageable pageable);
    Boolean existsById(int id);
    Request findById(long id);

}
