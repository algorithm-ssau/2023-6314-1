package com.team.productservice.view.mapper;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class Base64Mapper {
  public String view(byte[] content) {
    byte[] encodedBytes = Base64.encode(content);
    return "data:image/png;base64," + new String(encodedBytes);
  }

  public List<String> viewList(byte[][] content) {
    return Arrays.stream(content)
      .map(this::view)
      .toList();
  }
}
