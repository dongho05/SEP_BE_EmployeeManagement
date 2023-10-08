package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.Request;
import com.project.SEP_BE_EmployeeManagement.model.RequestCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestCategoryRepository extends JpaRepository<RequestCategory,Integer> {
    @Query(value = "select * from request_category r " +
            " where (:search is null or :search = '' or r.request_category_name LIKE %:search% ) " +
            " order by r.request_category_id",nativeQuery = true)
    Page<RequestCategory> getList(String search, Pageable pageable);
    Boolean existsById(int id);
    RequestCategory findById(int id);

}
