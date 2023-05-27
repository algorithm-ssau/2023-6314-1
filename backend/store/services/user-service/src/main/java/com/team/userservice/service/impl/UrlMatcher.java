package com.team.userservice.service.impl;

import com.team.userservice.service.exception.UrlMatchingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UrlMatcher {
  private final Pattern rootUrlPattern = Pattern.compile("\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#%?=~_|!:,.;]*[-a-zA-Z0-9+&@#%=~_|]");

  public String getUrlRoot(HttpServletRequest httpServletRequest) {
    Matcher matcher = rootUrlPattern.matcher(getRequestUrl(httpServletRequest));
    if (matcher.find()) {
      return matcher.group(0);
    } else throw new UrlMatchingException("Cannot match root url from: " + getRequestUrl(httpServletRequest));
  }

  private String getRequestUrl(HttpServletRequest httpServletRequest) {
    return httpServletRequest.getRequestURL().toString();
  }
}
