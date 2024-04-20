package com.trainingkaryawan.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "otp")
public class OtpEntity extends BaseEntity{
    private String email;
    private String otp;
    private boolean isVerified;
    private Date expirationTime;
}
