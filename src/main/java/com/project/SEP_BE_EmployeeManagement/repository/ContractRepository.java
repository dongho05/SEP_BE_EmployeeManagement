package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.Contract;
import com.project.SEP_BE_EmployeeManagement.model.Department;
import com.project.SEP_BE_EmployeeManagement.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query(value = "select * from contract where user_id = ?1 order by updated_date desc",nativeQuery = true)
    List<Contract> getContractsByUser(long userId);

    @Query(value = "SELECT d FROM Contract d WHERE (d.contractName IS NOT NULL) AND (d.contractName LIKE %:text%)  AND (:deptId IS NULL OR d.user.department.id = :deptId)")
    Page<Contract> findAllByContractNameIsNotNull(@Param("text") String search, @Param("deptId") Long deptId,Pageable pageable);
    //Page<Contract> findAllByContractNameIsNotNull(Pageable pageable);
    //Page<Contract> getAllByContractNameIsNotNull(); //AND (d.contractName LIKE %:search%)

    Optional<Contract> findById(long id);

    //lay tat ca id user da co
    @Query("SELECT DISTINCT c.user.id FROM Contract c WHERE c.user.id IS NOT NULL")
    List<Long> findDistinctUserIds();
}
