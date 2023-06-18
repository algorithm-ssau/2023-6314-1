package com.team.jwt.time;

import java.util.Date;

public interface Interval {
  Date getCreated();
  Date getExpired();
  long inMilliseconds();
}
