package com.project.SEP_BE_EmployeeManagement.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "user_image", nullable = false, length = 200)
    private String userImage;

    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 200)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private boolean status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "birth_day")
    private Timestamp birthDay;

    @Column(name = "gender")
    private boolean gender;


    // Many to One Có nhiều người ở 1 contract.
    @ManyToOne
    @JoinColumn(name = "contract_id") // thông qua khóa ngoại contract_id
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    @Column(name = "contract")
    private Contract contract;

    //    @ManyToOne
//    @JoinColumn(name = "position_id")
//    private Position position;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
