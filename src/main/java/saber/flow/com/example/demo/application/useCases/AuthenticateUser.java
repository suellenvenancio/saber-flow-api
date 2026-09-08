package saber.flow.com.example.demo.application.useCases;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.domain.model.User;
import saber.flow.com.example.demo.domain.repository.UserRepository;
import saber.flow.com.example.demo.infra.security.TokenService;

@Service
@Validated
@RequiredArgsConstructor
public class AuthenticateUser {
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepositoryAdapter;
  private final TokenService tokenService;

  public String execute(String email, String password) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(email, password));

    User user = userRepositoryAdapter.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

    return tokenService.newToken(user);
  }
}
