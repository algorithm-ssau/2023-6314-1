package com.team.identityprovider.service.contract;

import com.team.identityprovider.model.RefreshSession;

public interface RefreshSessionService {
  void save(RefreshSession refreshSession);
  void deleteByToken(String refreshToken);
  void deleteByUserId(long userId);
  boolean existsByToken(String refreshToken);
}
