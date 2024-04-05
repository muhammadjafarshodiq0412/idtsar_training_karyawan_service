package com.trainingkaryawan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class KaryawanEntity extends BaseEntity{

    private String nama;
    private String alamat;
    private String status;
    private Date dob;

    @OneToOne
    @JoinColumn(name = "detail_karyawan")
    private DetailKaryawanEntity detailKaryawan;

    @OneToMany(mappedBy = "karyawan", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<KaryawanTrainingEntity> karyawanTraining;

    @OneToOne(mappedBy = "karyawan")
    private RekeningEntity rekening;

}
