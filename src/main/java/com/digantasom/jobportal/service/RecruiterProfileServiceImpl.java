package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.RecruiterProfile;
import com.digantasom.jobportal.entity.Users;
import com.digantasom.jobportal.repository.RecruiterProfileRepository;
import com.digantasom.jobportal.repository.UsersRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileServiceImpl implements RecruiterProfileService {

  private final RecruiterProfileRepository recruiterProfileRepository;
  private final UsersRepository userRepository;

  public RecruiterProfileServiceImpl(RecruiterProfileRepository recruiterProfileRepository, UsersRepository userRepository) {
    this.recruiterProfileRepository = recruiterProfileRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Optional<RecruiterProfile> getOne(Integer id) {
    return recruiterProfileRepository.findById(id);
  }

  @Override
  public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
    return recruiterProfileRepository.save(recruiterProfile);
  }

  @Override
  public RecruiterProfile getCurrentRecruiterProfile() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUsername = authentication.getName();
      Users user = userRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"));
      Optional<RecruiterProfile> recruiterProfile = getOne(user.getUserId());
      return recruiterProfile.orElse(null);
    } else {
      return null;
    }
  }
}
