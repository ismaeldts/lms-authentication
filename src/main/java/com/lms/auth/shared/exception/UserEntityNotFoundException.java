package com.lms.auth.shared.exception;

public class UserEntityNotFoundException extends RuntimeException {

  public UserEntityNotFoundException(String message){
    super(message);
  }

}
