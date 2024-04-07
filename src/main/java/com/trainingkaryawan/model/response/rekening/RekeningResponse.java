package com.trainingkaryawan.model.response.rekening;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trainingkaryawan.entity.RekeningEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class RekeningResponse extends RekeningEntity {
        @JsonProperty("karyawan")
        private KaryawanModel karyawanModel;

        public RekeningResponse(RekeningEntity rekening, KaryawanModel karyawanModel) {
                super(rekening.getJenis(), rekening.getNama(), rekening.getRekening(), rekening.getKaryawan());
                super.setId(rekening.getId());
                super.setCreatedDate(rekening.getCreatedDate());
                super.setUpdatedDate(rekening.getUpdatedDate());
                super.setDeletedDate(rekening.getDeletedDate());
                this.karyawanModel = karyawanModel;
        }
}

