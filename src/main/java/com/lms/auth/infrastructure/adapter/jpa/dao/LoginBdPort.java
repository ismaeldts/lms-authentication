package com.lms.auth.infrastructure.adapter.jpa.dao;

import com.lms.auth.domain.model.dto.JwtResponseDTO;
import com.lms.auth.domain.model.dto.LoginRequestDTO;
import com.lms.auth.infrastructure.adapter.configuration.security.jwt.JwtGenerator;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@RequiredArgsConstructor
public class LoginBdPort {

  private final UserBdPort userBdPort;
  private final JwtGenerator jwtGenerator;
  private final AuthenticationManager authenticationManager;

  public JwtResponseDTO login(LoginRequestDTO userDTO) {
    String userRole = userBdPort.findByEmailOrPhoneNumber(userDTO.getLogin()).getRole();
    try {
      log.info("Autenticamos usuario " + userDTO);
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userDTO.getLogin(), userDTO.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String token = jwtGenerator.generateToken(authentication, userRole);
      String refreshToken = jwtGenerator.generateRefreshToken(authentication, userRole);
      return new JwtResponseDTO(token, refreshToken, LocalDateTime.now().plusDays(1));
    } catch (AuthenticationException ex) {
      log.error("Error auntenticando usuario ", ex);
      return null;
    } catch (Exception ex) {
      log.error("Error interno auntenticando usuario ", ex);
      return null;
    }

  }

}
