package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {
    @Query(value = "select * from request r " +
            " where (:search is null or :search = '' or r.request_title LIKE %:search% ) " +
            " and (:userId is null or :userId ='' or r.user_id = :userId) " +
            " order by r.request_id",nativeQuery = true)
    Page<Request> getList(String search, Pageable pageable, Long userId);
    Boolean existsById(int id);
    Request findById(long id);
}
