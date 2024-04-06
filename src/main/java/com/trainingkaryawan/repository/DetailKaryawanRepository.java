package com.trainingkaryawan.repository;

import com.trainingkaryawan.entity.DetailKaryawanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailKaryawanRepository extends JpaRepository<DetailKaryawanEntity, Long> {
}
