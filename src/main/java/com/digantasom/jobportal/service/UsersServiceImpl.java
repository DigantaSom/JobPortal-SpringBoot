package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.JobSeekerProfile;
import com.digantasom.jobportal.entity.RecruiterProfile;
import com.digantasom.jobportal.entity.Users;
import com.digantasom.jobportal.repository.JobSeekerProfileRepository;
import com.digantasom.jobportal.repository.RecruiterProfileRepository;
import com.digantasom.jobportal.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
  private final UsersRepository usersRepository;
  private final RecruiterProfileRepository recruiterProfileRepository;
  private final JobSeekerProfileRepository jobSeekerProfileRepository;

  public UsersServiceImpl(
      UsersRepository usersRepository,
      RecruiterProfileRepository recruiterProfileRepository,
      JobSeekerProfileRepository jobSeekerProfileRepository
  ) {
    this.usersRepository = usersRepository;
    this.recruiterProfileRepository = recruiterProfileRepository;
    this.jobSeekerProfileRepository = jobSeekerProfileRepository;
  }

  @Override
  public Users addNew(Users user) {
    user.setActive(true);
    user.setRegistrationDate(new Date(System.currentTimeMillis()));
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
}
