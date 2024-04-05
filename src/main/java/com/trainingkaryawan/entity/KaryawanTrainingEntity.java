package com.trainingkaryawan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "karyawan_training")
public class KaryawanTrainingEntity extends BaseEntity{
    private Date tanggal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_karyawan")
    private KaryawanEntity karyawan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_training")
    private TrainingEntity training;
}
