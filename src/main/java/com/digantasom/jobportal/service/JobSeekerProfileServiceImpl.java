package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.JobSeekerProfile;
import com.digantasom.jobportal.entity.Users;
import com.digantasom.jobportal.repository.JobSeekerProfileRepository;
import com.digantasom.jobportal.repository.UsersRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobSeekerProfileServiceImpl implements JobSeekerProfileService {

  private final JobSeekerProfileRepository jobSeekerProfileRepository;
  private final UsersRepository userRepository;

  public JobSeekerProfileServiceImpl(JobSeekerProfileRepository jobSeekerProfileRepository, UsersRepository userRepository) {
    this.jobSeekerProfileRepository = jobSeekerProfileRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Optional<JobSeekerProfile> getOne(Integer id) {
    return jobSeekerProfileRepository.findById(id);
  }

  @Override
  public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile) {
    return jobSeekerProfileRepository.save(jobSeekerProfile);
  }

  @Override
  public JobSeekerProfile getCurrentSeekerProfile() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUsername = authentication.getName();
      Users user = userRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"));
      Optional<JobSeekerProfile> jobSeekerProfile = getOne(user.getUserId());
      return jobSeekerProfile.orElse(null);
    } else {
      return null;
    }
  }
}
