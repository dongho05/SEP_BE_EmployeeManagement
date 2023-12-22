package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {
    @Query(value = "select r.*,u.department_id from request r " +
            " join users u on r.user_id = u.id " +
            " where (:search is null or :search = '' or r.request_title LIKE %:search% or u.user_code LIKE %:search% or u.full_name LIKE %:search% ) " +
            " and (:departmentId is null or :departmentId ='' or u.department_id = :departmentId) " +
            " and (:statusReq is null or :statusReq = '' or r.status = :statusReq) "+
            " and (:userId is null or :userId ='' or r.user_id = :userId) " +
            " and (:fromDate is null or :fromDate='' or r.create_date >= :fromDate) " +
            " and (:requestType is null or :requestType='' or r.request_type_id = :requestType) " +
            " and (:toDate is null or :toDate='' or r.create_date <= :toDate) " +
            " order by r.request_id",nativeQuery = true)
    Page<Request> getList(String search, String departmentId, String statusReq, Long userId, String fromDate, String toDate, String requestType, Pageable pageable);
    Boolean existsById(int id);
    Request findById(long id);

    @Query("SELECT r FROM Request r " +
            "WHERE MONTH(r.startDate) = MONTH(:date) " +
            "AND YEAR(r.startDate) = YEAR(:date) " +
            "AND MONTH(r.endDate) = MONTH(:date) " +
            "AND YEAR(r.endDate) = YEAR(:date) " +
            "AND DAY(r.endDate) <= DAY(:date) " +
            "AND r.status = 2" +
            "AND r.isCheck = false " +
            "ORDER BY r.createdDate" )
    List<Request> findRequestsAcceptedInCurrentMonth(LocalDate date);
    @Query(value = "select * from Request where  request_type_id = 4 and status in (1,2) \n" +
            "and start_date= :startDate\n" +
            "and user_id = :userId",
    nativeQuery = true)
    Page<Request> getListByUserIdAndStartDate(String startDate, Long userId,Pageable pageable);

}
