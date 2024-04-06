package com.trainingkaryawan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trainingkaryawan.constant.GeneralConstant;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Data
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern= GeneralConstant.YYYY_MM_DD)
    private Date createdDate;
    @JsonFormat(pattern= GeneralConstant.YYYY_MM_DD)
    private Date updatedDate;
    @JsonFormat(pattern= GeneralConstant.YYYY_MM_DD)
    private Date deletedDate;

    @PrePersist
    private void beforeSave() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"));
        this.createdDate = calendar.getTime();
    }

    @PreUpdate
    private void beforeUpdate() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"));
        this.updatedDate = calendar.getTime();
    }

    @PreRemove
    private void beforeDeleted() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"));
        this.deletedDate = calendar.getTime();
    }
}
