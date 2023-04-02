package com.team.productservice.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.productservice.rest.client.dto.ImageRequestDto;
import com.team.productservice.rest.client.dto.ImageResponseDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.web.reactive.function.client.WebClient.*;

@Component
public class ImageServiceClient {
  private final WebClient client;

  @Autowired
  public ImageServiceClient(WebClient imageServiceWebClient) {
    this.client = imageServiceWebClient;
  }

  public Long save(byte[] imageBytes) {
    RequestBodyUriSpec bodyUriSpec = client.post();
    RequestHeadersSpec<?> headersSpec = bodyUriSpec.uri("/api/images")
      .body(BodyInserters.fromValue(imageBytes));
    Mono<Long> idMono = headersSpec.retrieve().bodyToMono(Long.class);
    return Objects.requireNonNull(idMono.block());
  }

  public List<Long> saveAll(byte[][] imagesBytes) {
    List<Long> imagesId = new ArrayList<>();
    for (byte[] imageBytes : imagesBytes) {
      imagesId.add(save(imageBytes));
    }
    return imagesId;
  }
}
