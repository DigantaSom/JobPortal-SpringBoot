package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.JobPostActivity;
import com.digantasom.jobportal.entity.JobSeekerApply;
import com.digantasom.jobportal.entity.JobSeekerProfile;

import java.util.List;

public interface JobSeekerApplyService {
  List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId);
  List<JobSeekerApply> getJobCandidates(JobPostActivity job);
  void addNew(JobSeekerApply jobSeekerApply);
}
