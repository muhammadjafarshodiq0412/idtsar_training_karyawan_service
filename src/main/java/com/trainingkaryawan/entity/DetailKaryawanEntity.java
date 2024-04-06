package com.trainingkaryawan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trainingkaryawan.model.request.karyawan.DetailKaryawanSaveRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @JsonIgnore
    @OneToOne(mappedBy = "detailKaryawan", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private KaryawanEntity karyawan;

    public DetailKaryawanEntity(DetailKaryawanSaveRequest data) {
        this.nik = data.getNik();
        this.npwp = data.getNpwp();
    }

    @Override
    public String toString() {
        return "DetailKaryawanEntity{" +
                "nik='" + nik + '\'' +
                ", npwp='" + npwp + '\'' +
                '}';
    }
}
