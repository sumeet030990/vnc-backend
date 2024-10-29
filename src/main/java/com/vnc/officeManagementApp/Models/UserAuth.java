package com.vnc.officeManagementApp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
public class UserAuth implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    @JsonIgnore // Prevents password from being serialized i.e. it ll not come in responses
    private String password;

    @Column(columnDefinition = "tinyint(1) default 1")
    private Boolean isEnabled;

    @OneToOne(mappedBy = "userAuth")
    @ToString.Exclude // This will prevent infinite recursion
    private Users users;

    @Override
    @JsonIgnore // authorities aren’t needed, hide them too
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (users != null && users.getRoles() != null) {
            String roleName = users.getRoles().getName();
            return List.of(new SimpleGrantedAuthority(roleName));
        }
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
