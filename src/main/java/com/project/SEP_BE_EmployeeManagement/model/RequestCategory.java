package com.project.SEP_BE_EmployeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "request_category")
@Data
public class RequestCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_category_id")
    private int id;

    @Column(name = "request_category_name", nullable = false, length = 100)
    private String requestCategoryName;

    @OneToMany(mappedBy = "requestCategory",cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Set<RequestType> requestTypes = new HashSet<>();
}
