package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.JobSeekerProfile;

import java.util.Optional;

public interface JobSeekerProfileService {
  Optional<JobSeekerProfile> getOne(Integer id);
  JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile);
  JobSeekerProfile getCurrentSeekerProfile();
}
