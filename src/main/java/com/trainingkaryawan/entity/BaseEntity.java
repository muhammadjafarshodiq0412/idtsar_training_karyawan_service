package com.trainingkaryawan.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdDate;
    private Date updatedDate;
    private Date deletedDate;

    @PrePersist
    private void beforeSave() {
        this.createdDate = new Date();
    }

    @PreUpdate
    private void beforeUpdate() {
        this.updatedDate = new Date();
    }

    @PreRemove
    private void beforeDeleted() {
        this.deletedDate = new Date();
    }
}
