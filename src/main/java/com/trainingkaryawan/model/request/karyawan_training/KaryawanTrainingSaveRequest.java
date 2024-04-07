package com.trainingkaryawan.model.request.karyawan_training;

import lombok.Data;

@Data
public class KaryawanTrainingSaveRequest {
    private Long karyawan;
    private Long training;
    private String tanggal;
}
