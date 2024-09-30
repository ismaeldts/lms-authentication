package com.lms.auth.domain.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class JwtResponseDTO {

  private String token;
  private String refreshToken;
  private LocalDateTime expirationDate;

}
