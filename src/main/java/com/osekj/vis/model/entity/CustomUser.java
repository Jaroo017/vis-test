package com.osekj.vis.model.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUser extends User {

    private String role;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String role) {
        super(username, password, authorities);
        this.role = role;
    }
}
