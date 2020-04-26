package com.springboot.vhrend.model;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;



@Data
public class Hr implements UserDetails {
    private Integer id;
    private String name;
    private String phone;
    private String telephone;
    private String address;
    private Boolean enabled;
    private String username;
    private String password;
    private String userface;
    private String remark;
    private static final long serialVersionUID = 1L;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return true;
    }
}