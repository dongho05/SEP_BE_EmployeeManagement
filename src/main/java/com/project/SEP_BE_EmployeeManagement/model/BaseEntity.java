package com.project.SEP_BE_EmployeeManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Column(name = "create_date")
    @CreatedDate
    @JsonIgnore
    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
    private Date createdDate;
    @Column(name = "created_by")
    @CreatedBy
    @JsonIgnore
    private String createdBy;
    @Column(name = "updated_date")
    @LastModifiedDate
    @JsonIgnore
    @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
    private Date updatedDate;
    @Column(name = "updated_by")
    @LastModifiedBy
    @JsonIgnore
    private String updatedBy;



}
