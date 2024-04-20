package com.trainingkaryawan.repository;

import com.trainingkaryawan.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    Optional<OtpEntity> findByEmail(String email);

    Optional<OtpEntity> findByEmailAndOtp(String email, String otp);
}
