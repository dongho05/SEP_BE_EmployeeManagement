package com.project.SEP_BE_EmployeeManagement.model;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "note_category")
public class NoteCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "note_category_id", nullable = false)
    private Long noteCategoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "note_category_name",length = 20)
    private ENoteCatergory name;

}