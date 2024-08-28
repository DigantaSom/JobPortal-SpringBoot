package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.RecruiterProfile;
import com.digantasom.jobportal.repository.RecruiterProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileServiceImpl implements RecruiterProfileService {
  private final RecruiterProfileRepository recruiterProfileRepository;

  public RecruiterProfileServiceImpl(RecruiterProfileRepository recruiterProfileRepository) {
    this.recruiterProfileRepository = recruiterProfileRepository;
  }

  @Override
  public Optional<RecruiterProfile> getOne(Integer id) {
    return recruiterProfileRepository.findById(id);
  }

  @Override
  public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
    return recruiterProfileRepository.save(recruiterProfile);
  }
}
