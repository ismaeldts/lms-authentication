package com.lms.auth.infrastructure.adapter.configuration.security.jwt;

import com.lms.auth.infrastructure.adapter.configuration.security.userdetails.UserEntityDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtGenerator jwtGenerator;
  private final UserEntityDetailsService userEntityDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token = getJWTfromRequest(request);
    log.info("Token from HttpServletRequest: {}", token);
    if (Objects.nonNull(token) && jwtGenerator.validateToken(token)) {
      String username = jwtGenerator.getUsernameFromJWT(token);
      log.info("Username from JWT token: {}", username);
      UserDetails userDetails = userEntityDetailsService.loadUserByUsername(username);
      log.info("Authorities from JWT token: {}", userDetails.getAuthorities());
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
      authenticationToken.setDetails(userDetails);
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    filterChain.doFilter(request, response);
  }

  private String getJWTfromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    } else {
      return null;
    }
  }

}
