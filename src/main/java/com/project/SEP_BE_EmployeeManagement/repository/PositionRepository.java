package com.project.SEP_BE_EmployeeManagement.repository;

import com.project.SEP_BE_EmployeeManagement.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
//    @Query(value = "select * from position p join role r where p.position",nativeQuery = true)
//    public List<Position> GetListPosition();

    @Query(value = "SELECT d FROM Position d WHERE ?1 IS NULL OR  lower(d.positionName) like CONCAT('%', lower(?1), '%') ")
    Page<Position> getPosition(String search, Pageable pageable);
}
