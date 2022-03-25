package com.azure.laundry.laundry.security.jwt;

import java.util.Date;

import com.azure.laundry.laundry.security.services.UserDetailsImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



import io.jsonwebtoken.*;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${azure.app.jwtSecret}")
  private String jwtSecret;

  @Value("${azure.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  // public String generateJwtToken(UserDetailsImpl userPrincipal) {
  //   return generateTokenFromUsername(userPrincipal.getUsername());
  // }

  public String generateJwtToken(UserDetailsImpl userPrincipal) {
    return generateTokenFromEmail(userPrincipal.getEmail());
  }

  // public String generateTokenFromUsername(String username) {
  //   return Jwts.builder().setSubject(username).setIssuedAt(new Date())
  //       .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
  //       .compact();
  // }

  public String generateTokenFromEmail(String email) {
    return Jwts.builder().setSubject(email).setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  // public String getUserNameFromJwtToken(String token) {
  //   return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  // }

  public String getUserEmailFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

}