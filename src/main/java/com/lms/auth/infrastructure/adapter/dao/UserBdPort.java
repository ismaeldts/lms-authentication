package com.lms.auth.infrastructure.adapter.dao;

import com.lms.auth.domain.model.UserModel;
import com.lms.auth.domain.port.CreateUserModelPort;
import com.lms.auth.infrastructure.adapter.jpa.mapper.UserRepositoryMapper;
import com.lms.auth.infrastructure.adapter.jpa.repository.UserRepository;
import com.lms.auth.shared.exception.UserEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBdPort implements CreateUserModelPort {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserRepositoryMapper userRepositoryMapper;

  @Override
  public UserModel createUserModel(UserModel user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    return userRepositoryMapper.toEntity(userRepository.save(userRepositoryMapper.toDTO(user)));
  }

  public UserModel findByEmailOrPhoneNumber(String criteria) {
    return userRepositoryMapper.toEntity(userRepository.findByEmailOrPhoneNumber(criteria, criteria)
        .orElseThrow(() -> new UserEntityNotFoundException("User with that criterial was not found.")));
  }

}
