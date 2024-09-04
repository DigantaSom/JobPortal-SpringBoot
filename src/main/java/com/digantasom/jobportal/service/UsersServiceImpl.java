package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.JobSeekerProfile;
import com.digantasom.jobportal.entity.RecruiterProfile;
import com.digantasom.jobportal.entity.Users;
import com.digantasom.jobportal.repository.JobSeekerProfileRepository;
import com.digantasom.jobportal.repository.RecruiterProfileRepository;
import com.digantasom.jobportal.repository.UsersRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
  private final UsersRepository usersRepository;
  private final RecruiterProfileRepository recruiterProfileRepository;
  private final JobSeekerProfileRepository jobSeekerProfileRepository;
  private final PasswordEncoder passwordEncoder;

  public UsersServiceImpl(
      UsersRepository usersRepository,
      RecruiterProfileRepository recruiterProfileRepository,
      JobSeekerProfileRepository jobSeekerProfileRepository,
      PasswordEncoder passwordEncoder
  ) {
    this.usersRepository = usersRepository;
    this.recruiterProfileRepository = recruiterProfileRepository;
    this.jobSeekerProfileRepository = jobSeekerProfileRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Users addNew(Users user) {
    user.setActive(true);
    user.setRegistrationDate(new Date(System.currentTimeMillis()));
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    Users savedUser = usersRepository.save(user);
    int userTypeId = savedUser.getUserTypeId().getUserTypeId();

    if (userTypeId == 1) {
      recruiterProfileRepository.save(new RecruiterProfile(savedUser));
    } else {
      jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
    }

    return savedUser;
  }

  @Override
  public Optional<Users> getUserByEmail(String email) {
    return usersRepository.findByEmail(email);
  }

  @Override
  public Object getCurrentUserProfile() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String username = authentication.getName();
      Users user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not find user."));
      int userId = user.getUserId();

      if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
        RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
        return recruiterProfile;
      } else {
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
        return jobSeekerProfile;
      }
    }
    return null;
  }

  @Override
  public Users getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String username = authentication.getName();
      return usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not find user."));
    }
    return null;
  }

  @Override
  public Users findByEmail(String email) {
    return usersRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
