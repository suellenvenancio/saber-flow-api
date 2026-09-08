package saber.flow.com.example.demo.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.api.dto.request.LoginRequestDTO;
import saber.flow.com.example.demo.api.dto.response.TokenResponseDTO;
import saber.flow.com.example.demo.application.useCases.AuthenticateUser;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticateUser authenticateUser;

  @PostMapping("/login")
  public ResponseEntity<TokenResponseDTO> authenticate(@RequestBody LoginRequestDTO loginRequest) {
    String token = authenticateUser.execute(loginRequest.email(), loginRequest.password());
    return ResponseEntity.ok(new TokenResponseDTO(token));
  }
}
