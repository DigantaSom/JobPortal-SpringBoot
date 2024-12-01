package com.digantasom.jobportal.controller;

import com.digantasom.jobportal.entity.JobPostActivity;
import com.digantasom.jobportal.entity.JobSeekerProfile;
import com.digantasom.jobportal.entity.JobSeekerSave;
import com.digantasom.jobportal.entity.Users;
import com.digantasom.jobportal.service.JobPostActivityService;
import com.digantasom.jobportal.service.JobSeekerProfileService;
import com.digantasom.jobportal.service.JobSeekerSaveService;
import com.digantasom.jobportal.service.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class JobSeekerSaveController {

  private final UsersService userService;
  private final JobSeekerProfileService jobSeekerProfileService;
  private final JobPostActivityService jobPostActivityService;
  private final JobSeekerSaveService jobSeekerSaveService;

  public JobSeekerSaveController(
      UsersService userService,
      JobSeekerProfileService jobSeekerProfileService,
      JobPostActivityService jobPostActivityService,
      JobSeekerSaveService jobSeekerSaveService
  ) {
    this.userService = userService;
    this.jobSeekerProfileService = jobSeekerProfileService;
    this.jobPostActivityService = jobPostActivityService;
    this.jobSeekerSaveService = jobSeekerSaveService;
  }

  @PostMapping("job-details/save/{id}")
  public String save(@PathVariable("id") int jobPostId, JobSeekerSave jobSeekerSave) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUsername = authentication.getName();
      Users user = userService.findByEmail(currentUsername);

      Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
      JobPostActivity jobPostActivity = jobPostActivityService.getOne(jobPostId);

      if (seekerProfile.isPresent() && jobPostActivity != null) {
        jobSeekerSave.setUserId(seekerProfile.get());
        jobSeekerSave.setJob(jobPostActivity);
      } else {
        throw new RuntimeException("User not found");
      }
      jobSeekerSaveService.addNew(jobSeekerSave);
    }
    return "redirect:/dashboard/";
  }

  @GetMapping("saved-jobs/")
  public String savedJobs(Model model) {
    List<JobPostActivity> jobPosts = new ArrayList<>();
    Object currentUserProfile = userService.getCurrentUserProfile();
    List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidatesJobs((JobSeekerProfile) currentUserProfile);

    for (JobSeekerSave jobSeekerSave: jobSeekerSaveList) {
      jobPosts.add(jobSeekerSave.getJob());
    }
    model.addAttribute("jobPost", jobPosts);
    model.addAttribute("user", currentUserProfile);

    return "saved-jobs";
  }
}