package com.team.jwt.properties;

import com.team.jwt.time.CommonInterval;
import com.team.jwt.time.Interval;
import lombok.Value;

import java.security.Key;
import java.util.Date;

@Value
public class TokenMetadata {
  String header;
  Key secretKey;
  long validityDateInMilliseconds;

  public TokenMetadata(String header, Key secretKey, long validityDateInMilliseconds) {
    this.header = header;
    this.secretKey = secretKey;
    this.validityDateInMilliseconds = validityDateInMilliseconds;
  }

  public TokenMetadata(String header, Key secretKey, Date expired) {
    this.header = header;
    this.secretKey = secretKey;
    this.validityDateInMilliseconds = expired.getTime() - new Date().getTime();
  }

  public Interval expiredInterval() {
    return new CommonInterval(validityDateInMilliseconds);
  }
}
