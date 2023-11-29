package com.project.SEP_BE_EmployeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "noteCategory",cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Set<NoteLog> noteLogs = new HashSet<>();

}