package com.osekj.vis.security;

import com.osekj.vis.model.entity.CustomUser;
import com.osekj.vis.model.entity.Permission;
import com.osekj.vis.model.entity.User;
import com.osekj.vis.model.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CustomUser loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }

        return new CustomUser(user.getUsername(), user.getPassword(),
                getGrantedAuthorities(user.getRole().getPermissions()), user.getRole().getRoleName());
    }

    private static List<SimpleGrantedAuthority> getGrantedAuthorities(Set<Permission> permissions) {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName().name()))
                .collect(Collectors.toList());
    }
}
