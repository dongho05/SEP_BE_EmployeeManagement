package com.project.SEP_BE_EmployeeManagement.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "attendance")
public class Attendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attendance_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @Column(name = "date_log")
    private LocalDate dateLog;

    @Column(name = "time_in")
    private LocalTime timeIn;

    @Column(name = "time_out")
    private LocalTime timeOut;

    @Column(name = "regular_hour")
    private LocalTime regularHour;

    @Column(name = "over_time")
    private LocalTime overTime;

    @Column(name = "total_work")
    private LocalTime totalWork;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "signs_id")
    private Sign signs ;

    @Column(name = "leave_status")
    private String leaveStatus;

    @Column(name = "request_active")
    private boolean requestActive;

    public Attendance(User user, LocalDate dateLog) {
        this.user = user;
        this.dateLog = dateLog;
    }
}
