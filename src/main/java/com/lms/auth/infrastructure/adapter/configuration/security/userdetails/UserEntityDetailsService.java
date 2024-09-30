package com.lms.auth.infrastructure.adapter.configuration.security.userdetails;

import com.lms.auth.domain.model.UserModel;
import com.lms.auth.infrastructure.adapter.dao.UserBdPort;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserEntityDetailsService implements UserDetailsService {

  private final UserBdPort userBdPort;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserModel user = userBdPort.findByEmailOrPhoneNumber(username);
    SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(user.getRole());
    Collection<GrantedAuthority> authorities = List.of(adminAuthority);
    return new User(user.getEmail() != null ? user.getEmail() : user.getPhoneNumber(),
        user.getPassword(), authorities);
  }
}
