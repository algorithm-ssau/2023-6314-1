package com.team.productservice.rest.client;

import com.team.productservice.rest.client.dto.ImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImageServiceClient {
  private final WebClient client;
  private final ObjectMapper objectMapper;

  @Autowired
  public ImageServiceClient(WebClient imageServiceWebClient, ObjectMapper objectMapper) {
    this.client = imageServiceWebClient;
    this.objectMapper = objectMapper;
  }

  public ImageDto.Response.Common get(Long id) {
    return client.get()
      .uri("/api/images/" + id)
      .retrieve().bodyToMono(ImageDto.Response.Common.class)
      .block();
  }

  public List<ImageDto.Response.Common> getAll(List<Long> ids) {
    List<ImageDto.Response.Common> commonDtoRequests = new ArrayList<>();
    for (Long id : ids) {
      commonDtoRequests.add(get(id));
    }
    return commonDtoRequests;
  }

  public Long save(ImageDto.Request.Common commonDtoRequest) {
    return client.post()
      .uri("/api/images")
      .body(BodyInserters.fromValue(commonDtoRequest))
      .retrieve().bodyToMono(Long.class)
      .block();
  }

  public List<Long> saveAll(List<ImageDto.Request.Common> commonDtoRequests) {
    List<Long> imagesId = new ArrayList<>();
    for (ImageDto.Request.Common commonRequest : commonDtoRequests) {
      imagesId.add(save(commonRequest));
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
