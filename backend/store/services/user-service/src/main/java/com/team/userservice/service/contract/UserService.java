package com.team.userservice.service.contract;

import com.team.userservice.model.User;

import java.util.List;

public interface UserService {
  List<User> findAll();
  User findById(Long id);
  User findByEmail(String email);
  void deleteById(Long id);
  void create(User user);
  void update(User user);
  void activate(String email);
}
