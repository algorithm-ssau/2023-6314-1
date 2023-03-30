package com.team.authorizeservice.service.impl;

import com.team.authorizeservice.persistence.model.RefreshSession;
import com.team.authorizeservice.persistence.repository.RefreshSessionRepository;
import com.team.authorizeservice.service.api.RefreshSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class CommonRefreshSessionService implements RefreshSessionService {
  private static final Long MAX_COUNT_OF_SESSIONS_BY_USER = 5L;
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
    log.debug("Saved refresh session: {}", newRefreshSession);
  }

  private void deleteAllByUserIfExceeded(Long userId, long currentCountOfSessionByUser) {
    if (currentCountOfSessionByUser > MAX_COUNT_OF_SESSIONS_BY_USER) {
      repository.deleteAllByUserId(userId);
      log.debug("Refresh session by user with id: {} > {}, deleted him all sessions",
        userId, MAX_COUNT_OF_SESSIONS_BY_USER);
    }
  }

  private void updateIfExists(
    List<RefreshSession> savedRefreshSessions,
    RefreshSession newRefreshSession
  ) {
    for (RefreshSession savedRefreshSession : savedRefreshSessions) {
      if (isEqualsUserForSessions(savedRefreshSession, newRefreshSession)) {
        newRefreshSession.setId(savedRefreshSession.getId());
        log.debug("Updated refresh session: {} to {}", savedRefreshSession, newRefreshSession);
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
  public void deleteByToken(String refreshToken) {
    repository.deleteByRefreshToken(refreshToken);
    log.debug("Deleted refresh session by token: {}", refreshToken);
  }

  @Override
  public boolean existsByToken(String refreshToken) {
    var existsByRefreshToken = repository.existsByRefreshToken(refreshToken);
    log.debug("Checked refresh session token match requested token");
    return existsByRefreshToken;
  }
}
