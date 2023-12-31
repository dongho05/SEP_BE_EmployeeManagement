package com.project.SEP_BE_EmployeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "request")
@Data
@Setter
public class Request extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @Column(name = "request_title", nullable = false, length = 100)
    private String requestTitle;

    @Column(name = "content", nullable = false, length = 300)
    private String requestContent;

//    @Column(name = "request_type_id", nullable = false)
//    private int requestTypeId;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "accept_by")
    private long acceptBy;

    @Column(name = "accept_at")
    private LocalDate acceptAt;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "note")
    private String note;

    @Column(name = "is_Check")
    private boolean isCheck = false;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "request_type_id", nullable = false)
    private RequestType requestType;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
//    @ManyToMany
}
