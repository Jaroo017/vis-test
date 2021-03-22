package com.osekj.vis.model.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Stream;

public enum PermissionType {
    LIST_USERS,
    CREATE_USERS,
    DELETE_USERS,
    EDIT_USERS;

    public GrantedAuthority toGrantedAuthority() {
        return new SimpleGrantedAuthority(name());
    }

    public static PermissionType fromString(String s) {
        return Stream.of(PermissionType.values())
                .filter(value -> value.toString().equals(s))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }
}
