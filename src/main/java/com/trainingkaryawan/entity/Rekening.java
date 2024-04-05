package com.trainingkaryawan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Rekening extends BaseEntity{

    private String nama;
    private String jenis;
    private String nomorRekening;
    @ManyToOne
    private Karyawan karyawan;
}
