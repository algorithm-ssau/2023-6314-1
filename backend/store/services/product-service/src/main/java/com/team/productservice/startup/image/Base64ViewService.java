package com.team.productservice.startup.image;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;

@Service
public class Base64ViewService implements ViewService<String> {
  @Override
  public String view(byte[] content) {
    byte[] encodedBytes = Base64.encode(content);
    return "data:image/png;base64," + new String(encodedBytes);
  }
}
