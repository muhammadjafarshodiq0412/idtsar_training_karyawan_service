package com.trainingkaryawan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DetailKaryawan extends BaseEntity{

    private String nik;
    private String npwp;
    @OneToOne
    private Karyawan karyawan;
}
