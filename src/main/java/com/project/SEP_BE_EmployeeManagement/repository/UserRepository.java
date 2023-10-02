package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsernameAndPassword(String email, String password);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByUserCode(String userCode);
    Boolean existsByEmail(String email);
}
