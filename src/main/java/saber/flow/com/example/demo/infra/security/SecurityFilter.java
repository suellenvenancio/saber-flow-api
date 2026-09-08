package saber.flow.com.example.demo.infra.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import saber.flow.com.example.demo.infra.persistence.adapters.UserRepositoryAdapter;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
  private final TokenService tokenService;
  private final UserRepositoryAdapter userRepositoryAdapter;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = recuperarToken(request);
    if (token != null && tokenService.isValid(token)) {
      try {
        String userId = tokenService.getSubject(token);
        userRepositoryAdapter.findById(userId).ifPresent(user -> {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              user,
              null,
              user.getAuthorities()
            );
          SecurityContextHolder.getContext().setAuthentication(authentication);
        });
      } catch (Exception e) {
        SecurityContextHolder.clearContext();
      }
    }
    filterChain.doFilter(request, response);
  }

  private String recuperarToken(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }
    return null;
  }
}
