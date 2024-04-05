package com.trainingkaryawan.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Training extends BaseEntity{

    private String pengajar;
    private String tema;
}
