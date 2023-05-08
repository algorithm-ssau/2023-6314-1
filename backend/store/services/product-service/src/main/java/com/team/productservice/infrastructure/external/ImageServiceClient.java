package com.team.productservice.infrastructure.external;

import com.team.logger.stereotype.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Client
public class ImageServiceClient {
  private final WebClient client;

  @Autowired
  public ImageServiceClient(@Qualifier("imageServiceWebClientBuilder") WebClient.Builder clientBuilder) {
    this.client = clientBuilder.build();
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
