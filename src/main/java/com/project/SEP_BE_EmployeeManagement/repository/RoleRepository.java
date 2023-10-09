package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.ERole;
import com.project.SEP_BE_EmployeeManagement.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERole name);

    @Query(value = "SELECT r FROM Role r WHERE ?1 IS NULL OR  lower(r.roleName) like CONCAT('%', lower(?1), '%') ")
    Page<Role> getRole(String search, Pageable pageable);
}
