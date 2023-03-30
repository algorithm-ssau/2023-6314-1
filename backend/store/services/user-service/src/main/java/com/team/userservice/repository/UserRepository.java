package com.team.userservice.repository;

import com.team.userservice.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsById(Long id);
}
