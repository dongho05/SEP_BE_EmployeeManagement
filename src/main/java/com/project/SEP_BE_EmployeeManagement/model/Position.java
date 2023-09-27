package com.project.SEP_BE_EmployeeManagement.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private int id;

    @Column(name = "position_name", nullable = false, length = 200)
    private String positionName;

//    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
//    private Set<Role> roles;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "position_roles",
//            joinColumns = @JoinColumn(name = "position_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    Set<Role> roles = new HashSet<>();
}
