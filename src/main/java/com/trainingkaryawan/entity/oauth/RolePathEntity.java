package com.trainingkaryawan.entity.oauth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity(name = "oauth_role_path")
public class RolePathEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String name;
    private String pattern;
    private String method;
    @JsonIgnore
    @ManyToOne(targetEntity = RoleEntity.class, cascade = CascadeType.ALL)
    private RoleEntity role;
}