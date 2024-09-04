package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.JobPostActivity;
import com.digantasom.jobportal.entity.JobSeekerProfile;
import com.digantasom.jobportal.entity.JobSeekerSave;

import java.util.List;

public interface JobSeekerSaveService {
  List<JobSeekerSave> getCandidatesJobs(JobSeekerProfile userAccountId);
  List<JobSeekerSave> getJobCandidates(JobPostActivity job);
  void addNew(JobSeekerSave jobSeekerSave);
}
