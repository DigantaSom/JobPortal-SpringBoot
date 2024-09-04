package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.JobPostActivity;
import com.digantasom.jobportal.entity.RecruiterJobDTO;

import java.time.LocalDate;
import java.util.List;

public interface JobPostActivityService {
  JobPostActivity addNew(JobPostActivity jobPostActivity);
  List<RecruiterJobDTO> getRecruiterJobs(int recruiter);
  JobPostActivity getOne(int id);
  List<JobPostActivity> getAll();
  List<JobPostActivity> search(String job, String location, List<String> type, List<String> remote, LocalDate searchDate);
}
