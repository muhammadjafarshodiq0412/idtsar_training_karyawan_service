package com.trainingkaryawan.model.request.karyawan;

import lombok.Data;

@Data
public class KaryawanUpdateRequest extends KaryawanSaveRequest{
    private Long id;
}
