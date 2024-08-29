package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.JobPostActivity;
import com.digantasom.jobportal.repository.JobPostActivityRepository;
import org.springframework.stereotype.Service;

@Service
public class JobPostActivityServiceImpl implements JobPostActivityService {
  private final JobPostActivityRepository jobPostActivityRepository;

  public JobPostActivityServiceImpl(JobPostActivityRepository jobPostActivityRepository) {
    this.jobPostActivityRepository = jobPostActivityRepository;
  }

  @Override
  public JobPostActivity addNew(JobPostActivity jobPostActivity) {
    return jobPostActivityRepository.save(jobPostActivity);
  }
}
