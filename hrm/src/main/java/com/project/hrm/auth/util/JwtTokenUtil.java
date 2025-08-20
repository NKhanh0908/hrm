package com.project.hrm.auth.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    private final SecretKey secretKeyForAccessToken ;
    private final SecretKey secretKeyForRefreshToken = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtTokenUtil(
            @Value("${jwt.secret-key}") String jwtSecret,
            @Value("${jwt.access-expiration:900000}") long accessTokenExpiration,
            @Value("${jwt.refresh-expiration:604800000}") long refreshTokenExpiration
    ) {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.secretKeyForAccessToken = new SecretKeySpec(keyBytes, "HmacSHA256");
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    private Map<String, Object> extractRole(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", roles);
        return claims;
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(extractRole(userDetails))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(secretKeyForAccessToken)
                .compact();
    }

    public String generateRefreshToken( UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(extractRole(userDetails))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(secretKeyForRefreshToken)
                .compact();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(
                Jwts.parser().verifyWith(secretKeyForAccessToken).build().parseSignedClaims(token).getPayload()
        );
    }

    public String extractTokenGetUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public boolean isTokenExpired( String token) {
        return extractClaims( token, Claims::getExpiration).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractTokenGetUsername(token);
        if (!username.equals(userDetails.getUsername())) {
            throw new UsernameNotFoundException("null");
        }
        if (isTokenExpired(token)) {
            throw new UsernameNotFoundException("null");
        }
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}