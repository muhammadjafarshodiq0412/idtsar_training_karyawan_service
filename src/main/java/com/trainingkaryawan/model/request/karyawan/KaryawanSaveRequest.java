package com.trainingkaryawan.model.request.karyawan;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KaryawanSaveRequest {
    private String nama;
    private String dob;
    private String status;
    private String alamat;
    private DetailKaryawanSaveRequest detailKaryawan;
}
