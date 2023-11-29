package com.project.SEP_BE_EmployeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "note_log")
public class NoteLog  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "note_log_id", nullable = false)
    private Long note_log_id;

    @Column
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "admin_edit_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User adminEdit;

    @ManyToOne
    @JoinColumn(name = "attendance_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Attendance attendance;

    @ManyToOne
    @JoinColumn(name = "approvers_request_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User approversRequest;

    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_category_id", referencedColumnName = "note_category_id")
    private NoteCategory noteCategory;

    @OneToOne
    private Sign lastSign;

    @OneToOne
    private Sign signChange;

}
