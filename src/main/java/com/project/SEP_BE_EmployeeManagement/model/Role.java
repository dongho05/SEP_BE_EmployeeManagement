package com.project.SEP_BE_EmployeeManagement.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "role")
@Table(name = "role")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "role_name", nullable = false, length = 200)
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
}
