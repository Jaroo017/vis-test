package com.osekj.vis.security;

import com.osekj.vis.model.entity.CustomUser;
import com.osekj.vis.model.repository.UserRepository;
import com.osekj.vis.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String HEADER_STRING = "Authorization";

    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        String authToken = httpServletRequest.getHeader(HEADER_STRING);

        String username = null;
        if (authToken != null) {
            try {
                username = JwtTokenUtil.getUsernameFromToken(authToken);
                if (userRepository.findByUsername(username) == null) {
                    username = null;
                }
            } catch (IllegalArgumentException | ExpiredJwtException | SignatureException e) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUser userDetails = userDetailsService.loadUserByUsername(username);
            VisAuthenticationToken authentication =
                    new VisAuthenticationToken(userDetails, null, userDetails.getAuthorities(), userDetails.getRole());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}