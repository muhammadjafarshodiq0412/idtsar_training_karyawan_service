package com.trainingkaryawan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trainingkaryawan.constant.GeneralConstant;
import com.trainingkaryawan.model.request.karyawan.KaryawanSaveRequest;
import com.trainingkaryawan.util.DateUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "karyawan")
@NoArgsConstructor
@AllArgsConstructor
public class KaryawanEntity extends BaseEntity {

    private String nama;
    private String alamat;
    private String status;
    @JsonFormat(pattern = GeneralConstant.YYYY_MM_DD)
    private Date dob;
    @OneToOne
    @JoinColumn(name = "detail_karyawan")
    private DetailKaryawanEntity detailKaryawan;

    //todo remove bidirectional association, reason when do delete data not deleted
//    @JsonIgnore
//    @OneToMany(mappedBy = "karyawan", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
//    private List<KaryawanTrainingEntity> karyawanTraining;
//    @JsonIgnore
//    @OneToOne(mappedBy = "karyawan", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
//    private RekeningEntity rekening;

    public KaryawanEntity(KaryawanSaveRequest data) {
        this.nama = data.getNama();
        this.alamat = data.getAlamat();
        this.status = data.getStatus();
        this.dob = DateUtil.stringToDate(data.getDob(), GeneralConstant.YYYY_MM_DD);
    }

    //Override toString() to avoid recursion/infinite loop
    @Override
    public String toString() {
        return "KaryawanEntity{" +
                "nama='" + nama + '\'' +
                ", alamat='" + alamat + '\'' +
                ", status='" + status + '\'' +
                ", dob=" + dob +
                '}';
    }
}
