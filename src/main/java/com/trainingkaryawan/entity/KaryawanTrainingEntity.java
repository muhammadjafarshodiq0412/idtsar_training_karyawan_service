package com.trainingkaryawan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trainingkaryawan.constant.GeneralConstant;
import com.trainingkaryawan.model.request.karyawan_training.KaryawanTrainingSaveRequest;
import com.trainingkaryawan.util.DateUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "karyawan_training")
public class KaryawanTrainingEntity extends BaseEntity {
    @JsonFormat(pattern = GeneralConstant.YYYY_MM_DD)
    private Date tanggal;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_karyawan")
    private KaryawanEntity karyawan;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_training")
    private TrainingEntity training;

    public KaryawanTrainingEntity(KaryawanTrainingSaveRequest data) {
        this.tanggal = DateUtil.stringToDate(data.getTanggal(), GeneralConstant.YYYY_MM_DD_HH_MM_SS);
    }
}
