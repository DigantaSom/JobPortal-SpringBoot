package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.Users;
import com.digantasom.jobportal.repository.UsersRepository;
import com.digantasom.jobportal.util.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UsersRepository usersRepository;

  public CustomUserDetailsService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
    return new CustomUserDetails(user);
  }
}
