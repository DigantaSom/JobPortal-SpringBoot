package com.digantasom.jobportal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RecruiterJobDTO {
  private Long totalCandidates;
  private Integer jobPostId;
  private String jobTitle;
  private JobLocation jobLocationId;
  private JobCompany jobCompanyId;
}
