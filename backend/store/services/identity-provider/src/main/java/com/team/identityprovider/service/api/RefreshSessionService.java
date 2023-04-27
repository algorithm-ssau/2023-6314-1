package com.team.identityprovider.service.api;

import com.team.identityprovider.persistence.model.RefreshSession;

public interface RefreshSessionService {
  void save(RefreshSession refreshSession);
  void deleteByToken(String refreshToken);
  void deleteByUserId(long userId);
  boolean existsByToken(String refreshToken);
}
