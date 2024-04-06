package com.trainingkaryawan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trainingkaryawan.model.request.training.TrainingSaveRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "training")
public class TrainingEntity extends BaseEntity{
    private String pengajar;
    private String tema;

    @JsonIgnore
    @OneToMany(mappedBy = "training", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<KaryawanTrainingEntity> karyawanTraining;

    public TrainingEntity(TrainingSaveRequest data) {
        this.pengajar = data.getPengajar();
        this.tema = data.getTema();
    }
}
