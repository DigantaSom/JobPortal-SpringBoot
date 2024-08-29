package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.Users;

import java.util.Optional;

public interface UsersService {
  Users addNew(Users user);
  Optional<Users> getUserByEmail(String email);
  Object getCurrentUserProfile();
  Users getCurrentUser();
}
