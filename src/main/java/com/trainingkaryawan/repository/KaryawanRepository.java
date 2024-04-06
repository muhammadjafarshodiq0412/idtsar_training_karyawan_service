package com.trainingkaryawan.repository;

import com.trainingkaryawan.entity.DetailKaryawanEntity;
import com.trainingkaryawan.entity.KaryawanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KaryawanRepository extends JpaRepository<KaryawanEntity, Long> {
}
