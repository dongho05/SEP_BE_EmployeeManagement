package com.project.SEP_BE_EmployeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
//@Table(name = "TK_Sign")
public class Sign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sign_id", nullable = false)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "sign_name",length = 20)
    private ESign name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "signs")
    @JsonIgnore
    private Set<Attendance> attendances = new HashSet<>();


    public Sign(ESign nt) {
        this.name=nt;
    }
}
