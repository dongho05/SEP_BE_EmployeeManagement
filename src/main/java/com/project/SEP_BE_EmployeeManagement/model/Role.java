package com.project.SEP_BE_EmployeeManagement.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
}
