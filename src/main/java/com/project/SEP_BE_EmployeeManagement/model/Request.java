package com.project.SEP_BE_EmployeeManagement.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "request")
@Table(name = "request")
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private int id;

    @Column(name = "request_title", nullable = false, length = 100)
    private String requestTitle;

    @Column(name = "content", nullable = false, length = 300)
    private String requestContent;

    @Column(name = "request_type_id", nullable = false)
    private int requestTypeId;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "date_from")
    private Timestamp dateFrom;

    @Column(name = "date_to")
    private Timestamp dateTo;

    @Column(name = "create_by")
    private long createBy;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "accept_by", nullable = false)
    private long acceptBy;

    @Column(name = "accept_at")
    private Timestamp acceptAt;

    @Column(name = "status", nullable = false)
    private boolean status;

//    @ManyToMany
}
