package com.team.productservice.rest.client;

import com.team.productservice.startup.image.Base64ViewService;
import com.team.productservice.rest.client.dto.ImageDto;
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
  public ImageServiceClient(WebClient.Builder clientBuilder,
                            Base64ViewService base64ViewService) {
    this.client = clientBuilder.build();
    this.base64ViewService = base64ViewService;
  }

  public String get(Long id) {
    return client.get()
      .uri("/api/images/" + id)
      .retrieve().bodyToMono(String.class)
      .block();
  }

  public Long save(String content) {
    Long idMono = client.post()
      .uri("/api/images")
      .body(BodyInserters.fromValue(content))
      .retrieve().bodyToMono(Long.class).block();
    return Objects.requireNonNull(idMono);
  }

  public List<String> getAll(List<Long> ids) {
    List<String> imageContents = new ArrayList<>();
    for (Long id : ids) {
      imageContents.add(get(id));
    }
    return imageContents;
  }

  public List<Long> saveAll(List<String> contents) {
    List<Long> imagesId = new ArrayList<>();
    for (String content : contents) {
      imagesId.add(save(content));
    }
    return imagesId;
  }

  public void deletePack(List<Long> ids) {
    for (Long id : ids) {
      client.delete()
        .uri("/api/images/" + id)
        .retrieve().toBodilessEntity()
        .block();
    }
  }
}
