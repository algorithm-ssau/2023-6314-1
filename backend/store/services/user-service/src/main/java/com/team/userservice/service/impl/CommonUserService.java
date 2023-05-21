package com.team.userservice.service.impl;

import com.team.userservice.infrastructure.repository.UserRepository;
import com.team.userservice.model.User;
import com.team.userservice.model.exception.UserAlreadyExistsException;
import com.team.userservice.model.exception.UserNotFoundException;
import com.team.userservice.service.contract.UserService;
import com.team.userservice.validation.ValidUpdateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
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
    String lowerCaseEmail = email.toLowerCase();
    return userRepository.findByEmail(lowerCaseEmail).orElseThrow(
      () -> new UserNotFoundException("User with email" + email + "not found"));
  }

  @Override
  public void deleteById(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public void create(User user) {
    var lowerCaseEmail = user.getEmail().toLowerCase();
    user.setEmail(lowerCaseEmail);
    if (userRepository.findByEmail(lowerCaseEmail).isEmpty()) {
      userRepository.save(user);
    } else {
      throw new UserAlreadyExistsException("User with email " + lowerCaseEmail + " already exists");
    }
  }

  @Override
  public void update(Long id, @ValidUpdateUser User userWithEmptyFields) {
    User user = findById(id);
    user.setName(insuranceIfNull(user.getName(), userWithEmptyFields.getName()));
    user.setEmail(insuranceIfNull(user.getEmail(), userWithEmptyFields.getEmail()));
    user.setPassword(insuranceIfNull(user.getPassword(), userWithEmptyFields.getPassword()));
    user.setRole(insuranceIfNull(user.getRole(), userWithEmptyFields.getRole()));
    user.setActive(insuranceIfNull(user.getActive(), userWithEmptyFields.getActive()));
    user.setUpdated(OffsetDateTime.now());
  
    String lowerCaseEmail = user.getEmail().toLowerCase();
    user.setEmail(lowerCaseEmail);
    
    userRepository.save(user);
  }

  private <T> T insuranceIfNull(T reserve, T target) {
    return target == null ? reserve : target;
  }

  @Override
  public void activate(String email) {
    User user = findByEmail(email);
    user.setActive(true);
    update(user.getId(), user);
  }
}