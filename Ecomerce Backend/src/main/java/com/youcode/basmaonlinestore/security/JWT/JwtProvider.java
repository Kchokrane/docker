package com.youcode.basmaonlinestore.security.JWT;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
//@Slf4j // use this annotation will automatically generate a static variable called log
public class JwtProvider {

    // instead of declaring a logger variable
    // Use @Slf4j annotation for logging
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpiration}")
    private long jwtExpiration;

    // Generate Token
    public String generate(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Validate Token
    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error("JWT Authentication Failed");
        }
        return false;
    }

    // Get User From Token
    public String getUserAccount(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
                .getBody().getSubject();
    }
}
