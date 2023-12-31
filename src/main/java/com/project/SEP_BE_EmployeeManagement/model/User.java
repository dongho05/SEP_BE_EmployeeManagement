package com.project.SEP_BE_EmployeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "start_work")
    private LocalDate startWork;

    @Column(name = "end_work")
    private LocalDate endWork;

    @Column(name = "user_image")
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
    private Integer status; // 1 hieu luc; 0 vo hieu luc

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column(name = "gender")
    private Integer gender;// 1 nam; 0 nu

//    Số ngày nghỉ phép trong 1 năm
    @Column(name = "day_off")
    private Double dayoff;


    // Many to One Có nhiều người ở 1 contract.
//    @ManyToOne
//    @JoinColumn(name = "contract_id") // thông qua khóa ngoại contract_id
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
////    @Column(name = "contract")
//    private Contract contract;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // Quan hệ 1-n với đối tượng ở dưới (Person) (1 địa điểm có nhiều người ở)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Set<Contract> contracts;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Request> requests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Attendance> attendances = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<LogCheckInOut> logCheckInOuts= new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<LogInLateOutEarly> logInLateOutEarlies= new HashSet<>();
}
