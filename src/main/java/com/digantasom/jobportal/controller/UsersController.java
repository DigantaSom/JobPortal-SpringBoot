package com.digantasom.jobportal.controller;

import com.digantasom.jobportal.entity.Users;
import com.digantasom.jobportal.entity.UsersType;
import com.digantasom.jobportal.service.UsersService;
import com.digantasom.jobportal.service.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {
  private final UsersTypeService usersTypeService;
  private final UsersService usersService;

  public UsersController(UsersTypeService usersTypeService, UsersService usersService) {
    this.usersTypeService = usersTypeService;
    this.usersService = usersService;
  }

  @GetMapping("/register")
  public String register(Model model) {
    List<UsersType> usersTypes = usersTypeService.getAll();
    model.addAttribute("getAllTypes", usersTypes);
    model.addAttribute("user", new Users());
    return "register";
  }

  @PostMapping("/register/new")
  public String userRegistration(@Valid Users user, Model model) {
    Optional<Users> optionalUser = usersService.getUserByEmail(user.getEmail());

    if (optionalUser.isPresent()) {
      model.addAttribute("error", "Email already registered. Try again a different email.");
      List<UsersType> usersTypes = usersTypeService.getAll();
      model.addAttribute("getAllTypes", usersTypes);
      model.addAttribute("user", new Users());
      return "register";
    }
    usersService.addNew(user);
    return "redirect:/dashboard/"; // redirect to '/dashboard/'
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      new SecurityContextLogoutHandler().logout(request, response, authentication);
    }
    return "redirect:/"; // redirect to the home page
  }
}
