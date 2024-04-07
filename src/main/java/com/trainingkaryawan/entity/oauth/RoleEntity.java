package com.trainingkaryawan.entity.oauth;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity()
@Table(
        name = "oauth_role",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "role_name_and_type",
                        columnNames = {"type", "name"}
                )
        }
)
public class RoleEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String name;
    private String type;

//    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<RolePathEntity> rolePaths;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<UserEntity> users;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.name;
    }

    public RoleEntity( String name, String type) {
        this.name = name;
        this.type = type;
    }
}
