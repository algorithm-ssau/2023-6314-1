package com.team.productservice.rest.client;

import com.team.productservice.startup.image.Base64ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ImageServiceClient {
  private final WebClient client;
  private final Base64ViewService base64ViewService;

  @Autowired
  public ImageServiceClient(WebClient client,
                            Base64ViewService base64ViewService) {
    this.client = client;
    this.base64ViewService = base64ViewService;
  }

  public Long save(String content) {
    Long idMono = client.post()
      .uri("/api/images")
      .body(BodyInserters.fromValue(content))
      .retrieve().bodyToMono(Long.class).block();
    return Objects.requireNonNull(idMono);
  }

  public List<Long> saveAll(byte[][] contents) {
    List<Long> imagesId = new ArrayList<>();
    for (byte[] content : contents) {
      imagesId.add(save(base64ViewService.view(content)));
    }
    return imagesId;
  }
}
