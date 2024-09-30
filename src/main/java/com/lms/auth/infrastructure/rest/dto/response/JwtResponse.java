package com.lms.auth.infrastructure.rest.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class JwtResponse {
  private String token;
  private String refreshToken;
  private LocalDateTime expirationDate;
}
