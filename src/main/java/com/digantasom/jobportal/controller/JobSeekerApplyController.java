package com.digantasom.jobportal.controller;

import com.digantasom.jobportal.entity.*;
import com.digantasom.jobportal.service.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class JobSeekerApplyController {
  private final JobPostActivityService jobPostActivityService;
  private final UsersService userService;
  private final JobSeekerApplyService jobSeekerApplyService;
  private final JobSeekerSaveService jobSeekerSaveService;
  private final RecruiterProfileService recruiterProfileService;
  private final JobSeekerProfileService jobSeekerProfileService;

  public JobSeekerApplyController(
      JobPostActivityService jobPostActivityService,
      UsersService userService,
      JobSeekerApplyService jobSeekerApplyService,
      JobSeekerSaveService jobSeekerSaveService,
      RecruiterProfileService recruiterProfileService, JobSeekerProfileService jobSeekerProfileService
  ) {
    this.jobPostActivityService = jobPostActivityService;
    this.userService = userService;
    this.jobSeekerApplyService = jobSeekerApplyService;
    this.jobSeekerSaveService = jobSeekerSaveService;
    this.recruiterProfileService = recruiterProfileService;
    this.jobSeekerProfileService = jobSeekerProfileService;
  }

  @GetMapping("job-details-apply/{id}")
  public String display(@PathVariable int id, Model model) {
    JobPostActivity jobDetails = jobPostActivityService.getOne(id);
    List<JobSeekerApply> jobSeekerApplyList = jobSeekerApplyService.getJobCandidates(jobDetails);
    List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getJobCandidates(jobDetails);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
        RecruiterProfile user = recruiterProfileService.getCurrentRecruiterProfile();
        if (user != null) {
          model.addAttribute("applyList", jobSeekerApplyList);
        }
      } else {
        JobSeekerProfile user = jobSeekerProfileService.getCurrentSeekerProfile();
        if (user != null) {
          boolean exists = false;
          boolean saved = false;
          for (JobSeekerApply jobSeekerApply: jobSeekerApplyList) {
            if (Objects.equals(jobSeekerApply.getUserId().getUserAccountId(), user.getUserAccountId())) {
              exists = true;
              break;
            }
          }
          for (JobSeekerSave jobSeekerSave: jobSeekerSaveList) {
            if (Objects.equals(jobSeekerSave.getUserId().getUserAccountId(), user.getUserAccountId())) {
              saved = true;
              break;
            }
          }
          model.addAttribute("alreadyApplied", exists);
          model.addAttribute("alreadySaved", saved);
        }
      }
    }

    JobSeekerApply jobSeekerApply = new JobSeekerApply();
    model.addAttribute("applyJob", jobSeekerApply);

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

  @PostMapping("job-details/apply/{id}")
  public String apply(@PathVariable("id") int jobId, JobSeekerApply jobSeekerApply) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUsername = authentication.getName();
      Users user = userService.findByEmail(currentUsername);
      Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
      JobPostActivity jobPostActivity = jobPostActivityService.getOne(jobId);

      if (seekerProfile.isPresent() && jobPostActivity != null) {
        jobSeekerApply = new JobSeekerApply(); // to have the new candidates' information to be correctly added to the DB, if multiple candidates apply for the same job.
        jobSeekerApply.setUserId(seekerProfile.get());
        jobSeekerApply.setJob(jobPostActivity);
        jobSeekerApply.setApplyDate(new Date());
      } else {
        throw new RuntimeException("User not found");
      }
      jobSeekerApplyService.addNew(jobSeekerApply);
    }
    return "redirect:/dashboard/";
  }
}
