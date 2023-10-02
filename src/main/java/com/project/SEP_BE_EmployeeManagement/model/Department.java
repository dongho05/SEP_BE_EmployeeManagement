package com.project.SEP_BE_EmployeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Department")
@Data
public class Department extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "department_id", nullable = false)
    private Long id;
    @Column(name = "department_name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "department")
    @JsonIgnore
    private Set<User> users = new HashSet<>();
}
