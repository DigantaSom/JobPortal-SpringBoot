package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.JobPostActivity;
import com.digantasom.jobportal.entity.JobSeekerApply;
import com.digantasom.jobportal.entity.JobSeekerProfile;
import com.digantasom.jobportal.repository.JobSeekerApplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerApplyServiceImpl implements JobSeekerApplyService {

  private final JobSeekerApplyRepository jobSeekerApplyRepository;

  public JobSeekerApplyServiceImpl(JobSeekerApplyRepository jobSeekerApplyRepository) {
    this.jobSeekerApplyRepository = jobSeekerApplyRepository;
  }

  @Override
  public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId) {
    return jobSeekerApplyRepository.findByUserId(userAccountId);
  }

  @Override
  public List<JobSeekerApply> getJobCandidates(JobPostActivity job) {
    return jobSeekerApplyRepository.findByJob(job);
  }

  @Override
  public void addNew(JobSeekerApply jobSeekerApply) {
    jobSeekerApplyRepository.save(jobSeekerApply);
  }
}
