package com.team.identityprovider.service.api;

import com.team.identityprovider.persistence.model.RefreshSession;

public interface RefreshSessionService {
  void save(RefreshSession refreshSession);
  void deleteByToken(String refreshToken);
  boolean existsByToken(String refreshToken);
}
