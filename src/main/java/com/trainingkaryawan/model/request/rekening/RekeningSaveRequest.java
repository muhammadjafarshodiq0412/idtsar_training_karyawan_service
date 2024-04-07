package com.trainingkaryawan.model.request.rekening;

import lombok.Data;

@Data
public class RekeningSaveRequest {
    private String jenis;
    private String nama;
    private String rekening;
    private Long karyawan;
}
