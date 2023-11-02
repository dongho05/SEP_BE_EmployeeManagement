package com.project.SEP_BE_EmployeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "log_attendance_history")
public class LogAttendanceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attendance_history_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "attendance_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Attendance attendance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "new_signs_id")
    private Sign newSign ;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "old_signs_id")
    private Sign oldSign ;

    @Column(name = "updated_date")
    @JsonIgnore
    private String updatedDate;

    @Column(name = "updated_by")
    @JsonIgnore
    private String updatedBy;

    @Column(name = "reason")
    private String reason;
}
