package com.rln.chatgptapi.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;

@Slf4j
@Configuration
public class WebSecurityConfig {

  @Value("${chatgpt.security.user-name}")
  private String user;
  @Value("${chatgpt.security.password}")
  private String password;
  @Value("${chatgpt.security.origin}")
  private String origin;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
      .cors(Customizer.withDefaults())
      .csrf().disable()
      .authorizeRequests()
      .anyRequest()
      .authenticated()
      .and()
      .httpBasic()
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .build();
  }

  @Bean
  UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(
      User
        .withUsername(user)
        .password(passwordEncoder().encode(password))
        .roles()
        .build()
    );
    return manager;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Origin", "Content-Type"));
    corsConfiguration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST"));
    corsConfiguration.addAllowedOrigin("http://localhost:5173");
    corsConfiguration.setMaxAge(Duration.ZERO);
    corsConfiguration.setAllowCredentials(Boolean.TRUE);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

}
