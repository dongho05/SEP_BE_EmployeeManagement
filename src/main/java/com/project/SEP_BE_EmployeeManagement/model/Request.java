package com.project.SEP_BE_EmployeeManagement.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "request")
@Data
public class Request extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @Column(name = "request_title", nullable = false, length = 100)
    private String requestTitle;

    @Column(name = "content", nullable = false, length = 300)
    private String requestContent;

    @Column(name = "request_type_id", nullable = false)
    private int requestTypeId;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "accept_by", nullable = false)
    private long acceptBy;

    @Column(name = "accept_at")
    private LocalDate acceptAt;

    @Column(name = "status", nullable = false)
    private boolean status;

//    @ManyToMany
}
