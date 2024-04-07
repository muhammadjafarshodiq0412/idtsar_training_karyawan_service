package com.trainingkaryawan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trainingkaryawan.model.request.rekening.RekeningSaveRequest;
import jakarta.persistence.*;
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
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_karyawan")
    private KaryawanEntity karyawan;

    public RekeningEntity(RekeningSaveRequest data) {
        this.jenis = data.getJenis();
        this.nama = data.getNama();
        this.rekening = data.getRekening();
    }
}
