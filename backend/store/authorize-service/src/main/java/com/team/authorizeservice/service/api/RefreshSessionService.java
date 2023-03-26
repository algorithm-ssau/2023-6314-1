package com.team.authorizeservice.service.api;

import com.team.authorizeservice.persistence.model.RefreshSession;

public interface RefreshSessionService {
  void save(RefreshSession refreshSession);
  void deleteByToken(String refreshToken);
  boolean existsByToken(String refreshToken);
}
