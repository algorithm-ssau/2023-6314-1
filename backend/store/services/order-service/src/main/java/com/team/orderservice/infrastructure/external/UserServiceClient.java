package com.team.orderservice.infrastructure.external;

import com.team.basejwt.properties.TokenMetadata;
import com.team.logger.stereotype.Client;
import com.team.orderservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;


@Client
public class UserServiceClient {
  private final WebClient client;
  private final TokenMetadata tokenMetadata;

  @Autowired
  public UserServiceClient(@Qualifier("userServiceWebClientBuilder") WebClient.Builder clientBuilder,
                           TokenMetadata tokenMetadata) {
    this.client = clientBuilder.build();
    this.tokenMetadata = tokenMetadata;
  }

  public UserDto.Response.Common get(Long id, String token) {
    return client.get()
      .uri("/api/users/" + id)
      .header(tokenMetadata.getHeader(), "Bearer " + token)
      .retrieve()
      .bodyToMono(UserDto.Response.Common.class)
      .block();
  }
}
