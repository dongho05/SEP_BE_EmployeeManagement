package com.project.SEP_BE_EmployeeManagement.model;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
//@Table(name = "TK_NoteCatergory")
public class NoteCatergory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "note_catergory_id", nullable = false)
    private Long note_catergory_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "note_catergory_name",length = 20)
    private ENoteCatergory name;

}