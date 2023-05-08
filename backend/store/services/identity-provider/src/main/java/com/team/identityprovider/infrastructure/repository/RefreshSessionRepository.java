package com.team.identityprovider.infrastructure.repository;

import com.team.identityprovider.model.RefreshSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefreshSessionRepository extends JpaRepository<RefreshSession, Long> {
  List<RefreshSession> findByUserId(Long userId);
  void deleteAllByUserId(Long userId);
  void deleteByUserId(Long userId);
  void deleteByRefreshToken(String refreshToken);
  boolean existsByRefreshToken(String refreshToken);
}
