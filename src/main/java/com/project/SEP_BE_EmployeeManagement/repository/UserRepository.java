package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsernameAndPassword(String email, String password);
    Optional<User> findByUsername(String username);
    User findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query(value = "select u.* from users u " +
            " join department d on d.department_id = u.department_id" +
            " where (:departId = '' or u.department_id = :departId)  " +
            " and (:search is null or :search = '' or u.full_name LIKE %:search% or d.department_name LIKE %:search% or u.user_name LIKE %:search% or u.user_code LIKE %:search%) " +
            " and (:status = '' or u.status = :status) " +
            " order by u.updated_date desc ",nativeQuery = true)
    Page<User> getData( String departId, String search, String status, Pageable pageable);

    @Query(value = "select u.* from users u " +
            " join department d on d.department_id = u.department_id" +
            " where (:departId = '' or u.department_id = :departId)  " +
            " and (:search is null or :search = '' or u.full_name LIKE %:search% or d.department_name LIKE %:search% or u.user_name LIKE %:search% or u.user_code LIKE %:search%) " +
            " and (:status = '' or u.status = :status) " +
            " order by u.updated_date desc ",nativeQuery = true)
    List<User> getDataExport(String departId, String search, String status);

    boolean existsByUserCode(String userCode);
}
