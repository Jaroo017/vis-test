package com.osekj.vis.config


import org.springframework.security.core.GrantedAuthority

import static com.osekj.vis.model.enums.PermissionType.*

enum TestUser {
    ADMIN('admin', 'ADMIN_ROLE', Arrays.asList(LIST_USERS.toGrantedAuthority(),
            CREATE_USERS.toGrantedAuthority(),
            DELETE_USERS.toGrantedAuthority(),
            EDIT_USERS.toGrantedAuthority())),
    LIST_USER('list', 'LIST_USER_ROLE', Arrays.asList(LIST_USERS.toGrantedAuthority())),
    CREATE_USER('create', 'CREATE_USER_ROLE', Arrays.asList(CREATE_USERS.toGrantedAuthority())),
    EDIT_USER('edit', 'EDIT_USER_ROLE', Arrays.asList(EDIT_USERS.toGrantedAuthority())),
    DELETE_USER('delete', 'DELETE_USER_ROLE', Arrays.asList(DELETE_USERS.toGrantedAuthority()))

    String username
    String role
    List<GrantedAuthority> authorities

    TestUser(String username, String role, List<GrantedAuthority> authorities) {
        this.username = username
        this.role = role
        this.authorities = authorities
    }
}