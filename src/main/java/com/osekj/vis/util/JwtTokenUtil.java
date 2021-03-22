package com.osekj.vis.util;

import com.osekj.vis.model.entity.Permission;
import com.osekj.vis.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtTokenUtil {
    private static final String SIGNING_KEY = "ht8cGW5yKAOWdAIbf8gWK3lUBay4tsh8";
    private static final Long ACCESS_TOKEN_VALIDITY = 900000L;

    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("role", user.getRole().getRoleName());
        claims.put("authorities", user.getRole().getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName().name()))
                .collect(Collectors.toList()));

        return tokenGeneration(claims);
    }

    public static String generateToken(String username, String role, List<GrantedAuthority> grantedAuthorities) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        claims.put("authorities", grantedAuthorities);

        return tokenGeneration(claims);
    }

    private static String tokenGeneration(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("Vis-Test")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }
}
