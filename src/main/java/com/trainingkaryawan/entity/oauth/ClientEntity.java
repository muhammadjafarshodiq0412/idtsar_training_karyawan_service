package com.trainingkaryawan.entity.oauth;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Entity(name = "oauth_client")
public class ClientEntity implements ClientDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientId;
    private String clientSecret;
    private String scopes;
    private String grantTypes;
    private String redirectUris;
    private boolean approved;
    @Column(name = "access_token_expired")
    private Integer accessTokenValiditySeconds;
    @Column(name = "refresh_token_expired")
    private Integer refreshTokenValiditySeconds;
    @ManyToMany(targetEntity = RoleEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "oauth_client_role",
            joinColumns = {
                    @JoinColumn(name = "client_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            }
    )
    private Set<GrantedAuthority> authorities = new HashSet<>();

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        Set<String> resources = new HashSet<>();
        resources.add("oauth2-resource");

        return resources;
    }

    @Override
    public boolean isSecretRequired() {
        return !StringUtils.isEmpty(clientSecret);
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return !StringUtils.isEmpty(scopes);
    }
    @Override
    public Set<String> getScope() {
        Set<String> scope = new HashSet<>();
        if (isScoped()) {
            scope = new HashSet<>(Arrays.asList(scopes.split("\\s")));
        }
        return scope;
    }
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        if (null != grantTypes) {
            return new HashSet<>(Arrays.asList(grantTypes.split("\\s")));
        }
        return Collections.emptySet();
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        if (null != redirectUris) {
            return new HashSet<>(Arrays.asList(redirectUris.split("\\s")));
        }
        return Collections.emptySet();
    }
    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return approved;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return new HashMap<>();
    }
}
