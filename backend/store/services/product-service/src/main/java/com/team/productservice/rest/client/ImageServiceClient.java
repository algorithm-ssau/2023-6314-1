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
  private final ObjectMapper objectMapper;

  @Autowired
  public ImageServiceClient(WebClient imageServiceWebClient, ObjectMapper objectMapper) {
    this.client = imageServiceWebClient;
    this.objectMapper = objectMapper;
  }

  public ImageResponseDto get(Long id) {
    RequestHeadersUriSpec<?> requestHeadersUriSpec = client.get();
    RequestHeadersSpec<?> headersSpec = requestHeadersUriSpec.uri("/api/images/" + id);
    String response = headersSpec.retrieve().bodyToMono(String.class).block();
    return parseImageResponseDto(Objects.requireNonNull(response));
  }

  public Long save(@Valid ImageRequestDto dto) {
    RequestBodyUriSpec bodyUriSpec = client.post();
    RequestHeadersSpec<?> headersSpec = bodyUriSpec.uri("/api/images")
      .body(BodyInserters.fromValue(dto));
    Mono<Long> idMono = headersSpec.retrieve().bodyToMono(Long.class);
    return Objects.requireNonNull(idMono.block());
  }

  public List<Long> saveAll(List<ImageRequestDto> imagesRequestDto) {
    List<Long> imagesId = new ArrayList<>();
    for (ImageRequestDto imageRequestDto : imagesRequestDto) {
      imagesId.add(save(imageRequestDto));
    }
    return imagesId;
  }

  private ImageResponseDto parseImageResponseDto(String response) {
    try {
      return objectMapper.readValue(response.getBytes(), ImageResponseDto.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
