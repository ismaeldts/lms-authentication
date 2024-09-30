package com.lms.auth.infrastructure.adapter.configuration.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {


  public String generateToken(Authentication authentication, String userRole) {
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expiryDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(currentDate)
        .setExpiration(expiryDate)
        .signWith(new SecretKeySpec(Base64.getDecoder().decode(SecurityConstants.JWT_SECRET),
            SignatureAlgorithm.HS256.getJcaName()), SignatureAlgorithm.HS256)
        .claim("user_role", userRole)
        .compact();
  }

  // Generar el refresh token
  public String generateRefreshToken(Authentication authentication, String userRole) {
    return Jwts.builder()
        .setSubject(authentication.getName())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION * 3))
        .signWith(new SecretKeySpec(Base64.getDecoder().decode(SecurityConstants.JWT_SECRET),
            SignatureAlgorithm.HS256.getJcaName()), SignatureAlgorithm.HS256)
        .claim("user_role", userRole)
        .compact();
  }


  public String getUsernameFromJWT(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(new SecretKeySpec(Base64.getDecoder().decode(SecurityConstants.JWT_SECRET),
            SignatureAlgorithm.HS256.getJcaName()))
        .build()
        .parseClaimsJws(token)
        .getBody().getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(new SecretKeySpec(Base64.getDecoder().decode(SecurityConstants.JWT_SECRET),
              SignatureAlgorithm.HS256.getJcaName()))
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception ex) {
      throw new AuthenticationCredentialsNotFoundException("JWT token is not valid: " + token);
    }
  }

}
