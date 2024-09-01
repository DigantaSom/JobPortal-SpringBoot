package com.digantasom.jobportal.controller;

import com.digantasom.jobportal.entity.JobPostActivity;
import com.digantasom.jobportal.service.JobPostActivityService;
import com.digantasom.jobportal.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JobSeekerApplyController {
  private final JobPostActivityService jobPostActivityService;
  private final UsersService userService;

  public JobSeekerApplyController(JobPostActivityService jobPostActivityService, UsersService userService) {
    this.jobPostActivityService = jobPostActivityService;
    this.userService = userService;
  }

  @GetMapping("job-details-apply/{id}")
  public String display(@PathVariable int id, Model model) {
    JobPostActivity jobDetails = jobPostActivityService.getOne(id);
    model.addAttribute("jobDetails", jobDetails);
    model.addAttribute("user", userService.getCurrentUserProfile());
    return "job-details";
  }

  @PostMapping("dashboard/edit/{id}")
  public String editJob(@PathVariable int id, Model model) {
    JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
    model.addAttribute("jobPostActivity", jobPostActivity);
    model.addAttribute("user", userService.getCurrentUserProfile());
    return "add-jobs";
  }
}
