package com.osekj.vis.service;

import com.osekj.vis.dto.LoginDto;
import com.osekj.vis.dto.TokenDto;
import com.osekj.vis.model.entity.User;
import com.osekj.vis.model.repository.UserRepository;
import com.osekj.vis.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public TokenDto login(LoginDto loginUser) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final User user = userRepository.findByUsername(loginUser.getUsername());

        return new TokenDto(JwtTokenUtil.generateToken(user));
    }
}
