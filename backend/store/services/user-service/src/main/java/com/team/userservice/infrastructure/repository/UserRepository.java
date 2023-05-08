package com.team.userservice.infrastructure.repository;

import com.team.userservice.model.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsById(@NonNull Long id);
  Optional<User> findByEmail(String email);
}
