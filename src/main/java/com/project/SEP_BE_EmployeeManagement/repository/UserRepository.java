package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsernameAndPassword(String email, String password);
    @Query(value = "select u.* from users u where u.email = :username or u.user_name= :username ",nativeQuery = true)
    Optional<User> findByUsernameOrEmail(String username);
    Optional<User> findById(long id);
    User findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    User findByUserCode(String code);


    @Query(value = "select u from User u " +
            " join u.department d " +
            " where (:departId = '' or u.department.id = :departId) " +
            " and (:search is null or :search = '' or u.fullName LIKE %:search% or d.name LIKE %:search% or u.username LIKE %:search% or u.userCode LIKE %:search%) " +
            " and (:status = '' or u.status = :status) " +
            " order by u.id desc")
    Page<User> getData( String departId, String search, String status, Pageable pageable);

    @Query(value = "select u.* from users u " +
            " join department d on d.department_id = u.department_id" +
            " where (:status is null or :status = '' or u.status = :status)   " +
            " and (:search is null or :search = '' or u.full_name LIKE %:search% or d.department_name LIKE %:search% or u.user_name LIKE %:search% or u.user_code LIKE %:search%) " +
            " and ( :departId is null or :departId = '' or u.department_id = :departId) " +
            " order by u.id desc ",nativeQuery = true)
    List<User> getDataExport(String departId, String search, String status);


    boolean existsByUserCode(String userCode);

    @Query(value = "select u from User u where u.department.id = ?1")
    List<User> getUserByDepartment(long id);

    @Query(value = "select u from User u where u.position.id = ?1")
    List<User> getUserByPosition(long id);

    List<User> findAllByDepartmentId(long id);

    //lay tat ca user ma id khong trung voi id da co
    //List<User> findAllByIdNotIn(List<Long> userIds);
    //List<User> findAllByIdNotInAndDepartment_Id(List<Long> userIds, Long departmentId);

    List<User> findAllByDepartment_Id(Long departmentId);
}
