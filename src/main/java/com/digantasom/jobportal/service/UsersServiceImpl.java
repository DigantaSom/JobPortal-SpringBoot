package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.Users;
import com.digantasom.jobportal.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
  private final UsersRepository usersRepository;

  public UsersServiceImpl(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  @Override
  public Users addNew(Users user) {
    user.setActive(true);
    user.setRegistrationDate(new Date(System.currentTimeMillis()));
    return usersRepository.save(user);
  }

  @Override
  public Optional<Users> getUserByEmail(String email) {
    return usersRepository.findByEmail(email);
  }
}
