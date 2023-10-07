package com.project.SEP_BE_EmployeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "request_type")
@Data
public class RequestType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_type_id")
    private int id;

    @Column(name = "request_type_name", nullable = false, length = 100)
    private String requestTypeName;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "request_department_id", nullable = false)
    private RequestCategory requestCategory;

    @OneToMany(mappedBy = "requestType",cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Set<Request> requests = new HashSet<>();
}
