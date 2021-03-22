package com.osekj.vis.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;

public class VisAuthenticationToken extends PreAuthenticatedAuthenticationToken {

    @Getter
    private String role;

    public VisAuthenticationToken(Object aPrincipal, Object aCredentials,
                                  Collection<? extends GrantedAuthority> anAuthorities,
                                  String role) {
        super(aPrincipal, aCredentials, anAuthorities);
        this.role = role;
    }
}
