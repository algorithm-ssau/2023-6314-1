package com.team.userservice.service;

import com.team.userservice.data.User;
import com.team.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
  List<User> findAll();
  User findById(Long id);
  void deleteById(Long id);
  void save(User user);
  void update(User user);
}
