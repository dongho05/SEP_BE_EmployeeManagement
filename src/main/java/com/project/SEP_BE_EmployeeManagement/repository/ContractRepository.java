package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query(value = "select * from contract where user_id = ?1 order by updated_date desc",nativeQuery = true)
    List<Contract> getContractsByUser(long userId);
}
