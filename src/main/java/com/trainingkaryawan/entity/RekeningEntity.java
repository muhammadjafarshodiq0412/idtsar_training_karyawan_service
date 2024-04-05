package com.trainingkaryawan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Rekening")
public class RekeningEntity extends BaseEntity{

    private String jenis;
    private String nama;
    private String rekening;

    @OneToOne
    @JoinColumn(name = "id_karyawan")
    private KaryawanEntity karyawan;
}
