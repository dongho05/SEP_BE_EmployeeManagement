package com.project.SEP_BE_EmployeeManagement.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "role")
@Table(name = "role")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, length = 200)
    private ERole roleName;

//    @ManyToOne
//    @JoinColumn(name = "position_id")*

//    private Position position;
//
//    @ManyToMany(mappedBy = "roles")
//    Set<Position> positions;
}
