package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.ENoteCatergory;
import com.project.SEP_BE_EmployeeManagement.model.NoteCatergory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteCatergoryRepository extends JpaRepository<NoteCatergory,Long> {
    NoteCatergory findByName(ENoteCatergory name);
}
