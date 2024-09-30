package com.lms.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserModel {

  private String id;
  private String username;
  private String password;
  private String email;
  private String phoneNumber;
  private Integer age;
  private Boolean isActive;
  private String role;

}
