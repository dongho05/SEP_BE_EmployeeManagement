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
@Table(name = "log_in_late_out_early")
public class LogInLateOutEarly extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_inLateOutEarly_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @Column(name = "date_check")
    private LocalDate dateCheck;
    @Column(name = "time_start")
    private LocalTime timeStart;
    @Column(name = "time_end")
    private LocalTime timeEnd;
    @Column(name = "io_kind")
    private String IOKind;
    @Column(name = "duration")
    private LocalTime duration;
    @Column(name = "employee_id")
    private String EmployeeId;

    @Column(name = "reason")
    private String Reason;
}
