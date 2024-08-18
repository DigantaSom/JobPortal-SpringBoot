package com.digantasom.jobportal.config;

import com.digantasom.jobportal.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
  private final String[] publicUrls = {
      "/",
      "/global-search/**",
      "/register",
      "/register/**",
      "/webjars/**",
      "/resources/**",
      "/assets/**",
      "/css/**",
      "/summernote/**",
      "/js/**",
      "/*.css",
      "/*.js",
      "/*.js.map",
      "/fonts**", "/favicon.ico", "/resources/**", "/error"
  };
  private final CustomUserDetailsService customUserDetailsService;
  private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

  public WebSecurityConfig(
      CustomUserDetailsService customUserDetailsService,
      CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler
  ) {
    this.customUserDetailsService = customUserDetailsService;
    this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
  }

  @Bean
  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authenticationProvider(authenticationProvider());

    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers(publicUrls).permitAll();
      auth.anyRequest().authenticated(); // any other urls are protected
    });

    http.formLogin(form -> form.loginPage("/login").permitAll().successHandler(customAuthenticationSuccessHandler))
        .logout(logout -> {
          logout.logoutUrl("/logout");
          logout.logoutSuccessUrl("/");
        })
        .cors(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable());

    return http.build();
  }

  @Bean
  // to tell spring security how to find our users and how to authenticate passwords
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserDetailsService(customUserDetailsService);
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
