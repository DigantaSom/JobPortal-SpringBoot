package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.RecruiterProfile;

import java.util.Optional;

public interface RecruiterProfileService {
  Optional<RecruiterProfile> getOne(Integer id);
  RecruiterProfile addNew(RecruiterProfile recruiterProfile);
}
