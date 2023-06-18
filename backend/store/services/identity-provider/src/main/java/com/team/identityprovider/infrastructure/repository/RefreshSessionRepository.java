package com.team.identityprovider.infrastructure.repository;

import com.team.identityprovider.model.RefreshSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RefreshSessionRepository extends JpaRepository<RefreshSession, Long> {
  List<RefreshSession> findByUserId(Long userId);
  RefreshSession findByRefreshToken(String refreshToken);
  void deleteAllByUserId(Long userId);
  void deleteByUserId(Long userId);
  void deleteByRefreshToken(String refreshToken);
  boolean existsByRefreshToken(String refreshToken);

  @Query("delete from RefreshSession where expired < current_timestamp")
  void deleteExpired();
}
