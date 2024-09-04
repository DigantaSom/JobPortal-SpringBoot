package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.JobPostActivity;
import com.digantasom.jobportal.entity.JobSeekerProfile;
import com.digantasom.jobportal.entity.JobSeekerSave;
import com.digantasom.jobportal.repository.JobSeekerSaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerSaveServiceImpl implements JobSeekerSaveService {

  private final JobSeekerSaveRepository jobSeekerSaveRepository;

  public JobSeekerSaveServiceImpl(JobSeekerSaveRepository jobSeekerSaveRepository) {
    this.jobSeekerSaveRepository = jobSeekerSaveRepository;
  }

  public List<JobSeekerSave> getCandidatesJobs(JobSeekerProfile userAccountId) {
    return jobSeekerSaveRepository.findByUserId(userAccountId);
  }

  public List<JobSeekerSave> getJobCandidates(JobPostActivity job) {
    return jobSeekerSaveRepository.findByJob(job);
  }
}
