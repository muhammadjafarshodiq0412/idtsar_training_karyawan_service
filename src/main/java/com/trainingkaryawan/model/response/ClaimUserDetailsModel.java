package com.trainingkaryawan.model.response;

import com.trainingkaryawan.entity.oauth.RoleEntity;
import com.trainingkaryawan.entity.oauth.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ClaimUserDetailsModel {
    private Long id;
    private String nik;
    private String name;
    private String phoneNumber;
    private String email;
    private String username;
    private List<RoleEntity> role;
    private Boolean isActive;

    public ClaimUserDetailsModel(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.username = userEntity.getUsername();
        this.role = userEntity.getRoles();
        this.isActive = userEntity.getIsActive();
    }
}
