package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.*;
import com.digantasom.jobportal.repository.JobPostActivityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

  @Override
  public List<RecruiterJobDTO> getRecruiterJobs(int recruiter) {
    List<IRecruiterJob> recruiterJobDTOs = jobPostActivityRepository.getRecruiterJobs(recruiter);
    List<RecruiterJobDTO> recruiterJobDTOList = new ArrayList<>();

    for (IRecruiterJob rec: recruiterJobDTOs) {
      JobLocation loc = new JobLocation(rec.getLocationId(), rec.getCity(), rec.getState(), rec.getCountry());
      JobCompany comp = new JobCompany(rec.getCompanyId(), rec.getName(), "");
      recruiterJobDTOList.add(new RecruiterJobDTO(rec.getTotalCandidates(), rec.getJob_post_id(), rec.getJob_title(), loc, comp));
    }
    return recruiterJobDTOList;
  }

  @Override
  public JobPostActivity getOne(int id) {
    return jobPostActivityRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Job not found"));
  }
}
