package com.trainingkaryawan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "detail_karyawan")
public class DetailKaryawanEntity extends BaseEntity{
    private String nik;
    private String npwp;
    @OneToOne(mappedBy = "detailKaryawan")
    private KaryawanEntity karyawan;
}
