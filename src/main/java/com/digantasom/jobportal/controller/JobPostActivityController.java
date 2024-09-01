package com.digantasom.jobportal.controller;

import com.digantasom.jobportal.entity.JobPostActivity;
import com.digantasom.jobportal.entity.RecruiterJobDTO;
import com.digantasom.jobportal.entity.RecruiterProfile;
import com.digantasom.jobportal.entity.Users;
import com.digantasom.jobportal.service.JobPostActivityService;
import com.digantasom.jobportal.service.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller
public class JobPostActivityController {
  private final UsersService userService;
  private final JobPostActivityService jobPostActivityService;

  public JobPostActivityController(UsersService userService, JobPostActivityService jobPostActivityService) {
    this.userService = userService;
    this.jobPostActivityService = jobPostActivityService;
  }

  @GetMapping("/dashboard/")
  public String searchJobs(Model model) {
    Object currentUserProfile = userService.getCurrentUserProfile();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUsername = authentication.getName();
      model.addAttribute("username", currentUsername);

      if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
        List<RecruiterJobDTO> recruiterJobs = jobPostActivityService.getRecruiterJobs(((RecruiterProfile) currentUserProfile).getUserAccountId());
        model.addAttribute("jobPost", recruiterJobs);
      }
    }
    model.addAttribute("user", currentUserProfile);
    return "dashboard";
  }

  @GetMapping("/dashboard/add")
  public String addJobs(Model model) {
    model.addAttribute("jobPostActivity", new JobPostActivity());
    model.addAttribute("user", userService.getCurrentUserProfile());
    return "add-jobs";
  }

  @PostMapping("/dashboard/addNew")
  public String addNew(JobPostActivity jobPostActivity, Model model) {
    Users user = userService.getCurrentUser();
    if (user != null) {
      jobPostActivity.setPostedById(user);
      jobPostActivity.setPostedDate(new Date());
      model.addAttribute("jobPostActivity", jobPostActivity);
      jobPostActivityService.addNew(jobPostActivity);
    }
    return "redirect:/dashboard/";
  }
}
