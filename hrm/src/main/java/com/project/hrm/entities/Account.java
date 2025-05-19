package com.project.hrm.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.hrm.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account implements UserDetails {
    @Id
    private Integer id = IdGenerator.getGenerationId();
    private String username;
    private String password;
    private LocalDateTime createAt = LocalDateTime.now();
    private Boolean status = true;

    @OneToOne
    @JoinColumn
    private Employees employees;

    @ManyToOne
    @JoinColumn
    private Role role;

    public Account(Account account) {
        this.id = IdGenerator.getGenerationId();
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.createAt = LocalDateTime.now();
        this.status = true;
        this.employees = account.getEmployees();
        this.role = account.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.status;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
