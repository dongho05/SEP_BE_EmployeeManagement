package com.project.SEP_BE_EmployeeManagement.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "working_time")
@Data
public class WorkingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "working_time_id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "working_time_name", nullable = false, length = 100)
    private EWorkingTime workingTimeName;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;
}
