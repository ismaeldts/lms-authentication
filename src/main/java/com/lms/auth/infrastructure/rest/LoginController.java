package com.lms.auth.infrastructure.rest;

import com.lms.auth.domain.model.dto.JwtResponseDTO;
import com.lms.auth.domain.model.dto.LoginRequestDTO;
import com.lms.auth.infrastructure.adapter.configuration.security.jwt.JwtGenerator;
import com.lms.auth.infrastructure.adapter.jpa.dao.LoginBdPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/v1/login")
@RestController
@RequiredArgsConstructor
public class LoginController {

  private final LoginBdPort loginBdPort;

  @PostMapping(path = "/login")
  public ResponseEntity<JwtResponseDTO> login(@NonNull @RequestBody LoginRequestDTO loginRequest) {
    return new ResponseEntity<>(loginBdPort.login(loginRequest), HttpStatus.OK);
  }

}
