package saber.flow.com.example.demo.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import saber.flow.com.example.demo.domain.model.User;

@Service
public class TokenService {
  @Value("${api.security.secret}")
  private String secret;

  public String newToken(User user) {
    Algorithm algorithm = Algorithm.HMAC256(secret);

    return JWT.create()
        .withIssuer("SaberFlow")
        .withSubject(user.getId())
        .withExpiresAt(Date.from(expirationDate()))
        .sign(algorithm);
  }

  private Instant expirationDate() {
    return LocalDateTime.now()
        .plusHours(2)
        .toInstant(ZoneOffset.of("-03:00"));
  }

  public String getSubject(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer("SaberFlow")
          .build()
          .verify(token)
          .getSubject();

    } catch (JWTVerificationException e) {
      throw new RuntimeException("Invalid token", e);
    }
  }

  public boolean isValid(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      JWT.require(algorithm)
          .withIssuer("SaberFlow")
          .build()
          .verify(token);
      return true;
    } catch (JWTVerificationException e) {
      return false;
    }
  }
}
