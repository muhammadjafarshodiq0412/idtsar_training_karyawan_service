package com.trainingkaryawan.entity.oauth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "oauth_user")
public class UserEntity implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, unique = true)
    private String username;
    private String email;
    @Column(name = "fullname", length = 100)
    private String fullName;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String verifyToken;
    @JsonIgnore
    private Date expiredVerifyToken;
    @Column(length = 100)
    private String otp;
    private Date otpExpiredDate;
    private Boolean isActive;
    @JsonIgnore
    private boolean enabled = true;
    @JsonIgnore
    @Column(name = "not_expired")
    private boolean accountNonExpired = true;
    @JsonIgnore
    @Column(name = "not_locked")
    private boolean accountNonLocked = true;
    @JsonIgnore
    @Column(name = "credential_not_expired")
    private boolean credentialsNonExpired = true;
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "oauth_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<RoleEntity> roles = new ArrayList<>();

    public UserEntity(String username, String email, String fullName) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
    }

    public void addRole(RoleEntity item) {
        this.roles.add(item);
        item.getUsers().add(this);
    }

    public void removeRole(RoleEntity item) {
        this.roles.remove(item);
        item.getUsers().remove(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        this.roles.forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

