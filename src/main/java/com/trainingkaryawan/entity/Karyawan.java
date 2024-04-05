package com.trainingkaryawan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Karyawan extends BaseEntity{

    private String nama;
    private String alamat;
    private String status;

    @OneToOne
    private DetailKaryawan detailKaryawan;
    @OneToMany
    List<KaryawanTraining> karyawanTraining;
    @OneToMany
    private List<Rekening> rekenings;

}
