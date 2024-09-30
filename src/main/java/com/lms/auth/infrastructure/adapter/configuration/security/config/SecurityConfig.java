package com.lms.auth.infrastructure.adapter.configuration.security.config;

import com.lms.auth.infrastructure.adapter.configuration.security.jwt.JwtAuthEntryPoint;
import com.lms.auth.infrastructure.adapter.configuration.security.jwt.JwtAuthenticationFilter;
import com.lms.auth.infrastructure.adapter.configuration.security.jwt.JwtGenerator;
import com.lms.auth.infrastructure.adapter.configuration.security.userdetails.UserEntityDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserEntityDetailsService userEntityDetailsService;

  private final JwtAuthEntryPoint jwtAuthEntryPoint;
  private final String API_PREFIX = "/api/v1/";

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults());
    http.csrf(AbstractHttpConfigurer::disable);
    http.sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(
        SessionCreationPolicy.STATELESS));
    http.exceptionHandling(
        exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint));
    http.authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
        .requestMatchers(API_PREFIX + "/login", API_PREFIX + "/register")
        .permitAll()
        .anyRequest()
        .authenticated()
    );
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(
      JwtGenerator jwtGenerator,
      UserEntityDetailsService userEntityDetailsService) {
    return new JwtAuthenticationFilter(jwtGenerator, userEntityDetailsService);
  }

}
