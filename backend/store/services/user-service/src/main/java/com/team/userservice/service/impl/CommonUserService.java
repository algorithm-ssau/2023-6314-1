package com.team.userservice.service.impl;

import com.team.userservice.model.User;
import com.team.userservice.model.exception.UserAlreadyExistsException;
import com.team.userservice.model.exception.UserNotFoundException;
import com.team.userservice.infrastructure.repository.UserRepository;
import com.team.userservice.service.contract.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommonUserService implements UserService {
  private final UserRepository userRepository;

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User findById(Long id) {
    return userRepository.findById(id)
      .orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found"));
  }

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(
      () -> new UserNotFoundException("User with email" + email + "not found"));
  }

  @Override
  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public void create(User user) {
    var email = user.getEmail();
    if (userRepository.findByEmail(email).isEmpty()) {
      userRepository.save(user);
    } else {
      throw new UserAlreadyExistsException("User with email " + email + " already exists");
    }
  }

  @Override
  public void update(User user) {
    Long userId = user.getId();
    if (userId == null) {
      throw new UserNotFoundException("Cannot update user without id");
    } else if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException("Cannot find user with id: " + userId);
    }
    userRepository.save(user);
  }

  @Override
  public void activate(String email) {
    User user = findByEmail(email);
    user.setActive(true);
    update(user);
  }
}