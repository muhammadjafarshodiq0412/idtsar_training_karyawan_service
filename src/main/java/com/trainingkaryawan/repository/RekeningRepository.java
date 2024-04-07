package com.trainingkaryawan.repository;

import com.trainingkaryawan.entity.RekeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RekeningRepository extends JpaRepository<RekeningEntity, Long> {

}
