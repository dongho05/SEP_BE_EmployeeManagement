package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.RequestType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestTypeRepository extends JpaRepository<RequestType,Integer> {
    @Query(value = "select * from request_type r " +
            " where (:search is null or :search = '' or r.request_type_name LIKE %:search% ) " +
            " order by r.request_type_id",nativeQuery = true)
    Page<RequestType> getList(String search, Pageable pageable);
    Boolean existsById(int id);
    RequestType findById(int id);
}
