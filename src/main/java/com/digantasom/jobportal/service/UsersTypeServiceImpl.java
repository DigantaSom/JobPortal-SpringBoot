package com.digantasom.jobportal.service;

import com.digantasom.jobportal.entity.UsersType;
import com.digantasom.jobportal.repository.UsersTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeServiceImpl implements UsersTypeService {
  private final UsersTypeRepository usersTypeRepository;

  public UsersTypeServiceImpl(UsersTypeRepository usersTypeRepository) {
    this.usersTypeRepository = usersTypeRepository;
  }

  @Override
  public List<UsersType> getAll() {
    return usersTypeRepository.findAll();
  }
}
