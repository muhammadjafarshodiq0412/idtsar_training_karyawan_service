package com.trainingkaryawan.model.response.rekening;

import com.trainingkaryawan.entity.KaryawanEntity;
import lombok.Data;

@Data
public class KaryawanModel {
    private Long id;
    private String nama;

    public KaryawanModel(KaryawanEntity rekening) {
        this.id = rekening.getId();
        this.nama = rekening.getNama();
    }
}
