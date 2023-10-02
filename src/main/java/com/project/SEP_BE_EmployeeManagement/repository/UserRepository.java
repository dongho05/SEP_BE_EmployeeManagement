package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsernameAndPassword(String email, String password);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByUserCode(String userCode);
    Boolean existsByEmail(String email);

    @Query(value = "select u.* from users u " +
            " join department d on d.department_id = u.department_id" +
            " where (:departId = -1 or u.department_id = :departId)  " +
            " and (:code = '' or u.user_code = :code ) " +
            " and (:search is null or :search = '' or u.full_name LIKE %:search% or d.department_name LIKE %:search% or u.user_name LIKE %:search%) " +
            " and (:status is null or :status = '' or u.status = :status) " +
            " order by u.updated_date desc ",nativeQuery = true)

    Page<User> getData(String code, Integer departId, String search, String status, Pageable pageable);

}
