package com.digantasom.jobportal.controller;

import com.digantasom.jobportal.entity.RecruiterProfile;
import com.digantasom.jobportal.entity.Users;
import com.digantasom.jobportal.repository.UsersRepository;
import com.digantasom.jobportal.service.RecruiterProfileService;
import com.digantasom.jobportal.util.FileUploadUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {
  private final UsersRepository userRepository;
  private final RecruiterProfileService recruiterProfileService;

  public RecruiterProfileController(UsersRepository userRepository, RecruiterProfileService recruiterProfileService) {
    this.userRepository = userRepository;
    this.recruiterProfileService = recruiterProfileService;
  }

  @GetMapping("/")
  public String recruiterProfile(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUsername = authentication.getName();
      Users user = userRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not find user."));
      Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(user.getUserId());
      recruiterProfile.ifPresent(profile -> model.addAttribute("profile", profile));
    }
    return "recruiter_profile";
  }

  @PostMapping("/addNew")
  public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image") MultipartFile multipartFile, Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUsername = authentication.getName();
      Users user = userRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("Could not find user."));
      recruiterProfile.setUserId(user);
      recruiterProfile.setUserAccountId(user.getUserId());
    }
    model.addAttribute("profile", recruiterProfile);

    String fileName = "";
    if (!Objects.equals(multipartFile.getOriginalFilename(), "")) {
      fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
      recruiterProfile.setProfilePhoto(fileName);
    }
    RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);

    String uploadDir = "photos/recruiter" + savedUser.getUserAccountId();

    try {
      FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return "redirect:/dashboard/";
  }
}
