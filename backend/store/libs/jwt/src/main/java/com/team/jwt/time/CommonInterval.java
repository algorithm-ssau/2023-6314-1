package com.team.jwt.time;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Value;

import java.util.Date;

@Value
public class CommonInterval implements Interval {
  @PastOrPresent Date created = new Date();
  @FutureOrPresent Date expired;

  public CommonInterval(Long expiredInMls) {
    this.expired = new Date(created.getTime() + expiredInMls);
  }

  @Override
  public long inMilliseconds() {
    return expired.getTime() - created.getTime();
  }
}
