package com.team.identityprovider.service.impl;

import com.team.identityprovider.model.RefreshSession;
import com.team.identityprovider.infrastructure.repository.RefreshSessionRepository;
import com.team.identityprovider.service.contract.RefreshSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommonRefreshSessionService implements RefreshSessionService {
  private static final Long MAX_COUNT_OF_SESSIONS_BY_USER = 2L;
  private final RefreshSessionRepository repository;

  @Autowired
  public CommonRefreshSessionService(RefreshSessionRepository repository) {
    this.repository = repository;
  }

  @Override
  public void save(RefreshSession newRefreshSession) {
    var userId = newRefreshSession.getUserId();
    var savedRefreshSessions = repository.findByUserId(userId);
    var currentCountOfSessionByUser = savedRefreshSessions.size();
    deleteAllByUserIfExceeded(userId, currentCountOfSessionByUser);
    updateIfExists(savedRefreshSessions, newRefreshSession);

    repository.save(newRefreshSession);
  }

  private void deleteAllByUserIfExceeded(Long userId, long currentCountOfSessionByUser) {
    if (currentCountOfSessionByUser > MAX_COUNT_OF_SESSIONS_BY_USER) {
      repository.deleteAllByUserId(userId);
    }
  }

  private void updateIfExists(
    List<RefreshSession> savedRefreshSessions,
    RefreshSession newRefreshSession
  ) {
    for (RefreshSession savedRefreshSession : savedRefreshSessions) {
      if (isEqualsUserForSessions(savedRefreshSession, newRefreshSession)) {
        newRefreshSession.setId(savedRefreshSession.getId());
        break;
      }
    }
  }

  private boolean isEqualsUserForSessions(RefreshSession savedRefreshSession, RefreshSession newRefreshSession) {
    return savedRefreshSession.getUserId().equals(newRefreshSession.getUserId())
        && savedRefreshSession.getUserIp().equals(newRefreshSession.getUserIp())
        && savedRefreshSession.getUserBrowserFingerPrint().equals(newRefreshSession.getUserBrowserFingerPrint());
  }

  @Override
  public void deleteByUserId(long userId) {
    repository.deleteByUserId(userId);
  }

  @Override
  public void deleteByToken(String refreshToken) {
    repository.deleteByRefreshToken(refreshToken);
  }

  @Override
  public boolean existsByToken(String refreshToken) {
    return repository.existsByRefreshToken(refreshToken);
  }
}
