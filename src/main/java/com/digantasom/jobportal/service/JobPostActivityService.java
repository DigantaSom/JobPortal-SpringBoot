package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.JobPostActivity;
import com.digantasom.jobportal.entity.RecruiterJobDTO;

import java.util.List;

public interface JobPostActivityService {
  JobPostActivity addNew(JobPostActivity jobPostActivity);
  List<RecruiterJobDTO> getRecruiterJobs(int recruiter);
  JobPostActivity getOne(int id);
}
