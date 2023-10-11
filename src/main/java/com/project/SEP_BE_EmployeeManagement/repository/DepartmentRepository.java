package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.Department;
import com.project.SEP_BE_EmployeeManagement.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "SELECT d FROM Department d WHERE ?1 IS NULL OR  lower(d.name) like CONCAT('%', lower(?1), '%') ")
    Page<Department> getDepartment(String search, Pageable pageable);

    Optional<Department> findById(long id);
}
