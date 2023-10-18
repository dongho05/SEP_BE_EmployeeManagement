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
@Table(name = "log_check_in_out")
public class LogCheckInOut extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_checkInOut_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @Column(name = "date_check")
    private LocalDate dateCheck;

    @Column(name = "time_check")
    private LocalTime timeCheck;

    @Column(name = "badge_number")
    private String badgeNumber;
}
