package com.team.identityprovider.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;

@Value
public class RequestMetadata {
  String remoteAddress;
  String userAgent;

  public RequestMetadata(HttpServletRequest request) {
    remoteAddress = request.getRemoteAddr();
    userAgent = request.getHeader("user-agent");
  }
}


