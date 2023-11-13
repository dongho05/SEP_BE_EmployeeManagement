package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.Contract;
import com.project.SEP_BE_EmployeeManagement.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query(value = "select * from contract where user_id = ?1 order by updated_date desc",nativeQuery = true)
    List<Contract> getContractsByUser(long userId);

    @Query(value = "SELECT d FROM Contract d WHERE ?1 IS NULL OR  lower(d.contractName) like CONCAT('%', lower(?1), '%') ")
    Page<Contract> getContract(String search, Pageable pageable);
}
