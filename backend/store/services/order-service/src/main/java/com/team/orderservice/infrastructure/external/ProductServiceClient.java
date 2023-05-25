package com.team.orderservice.infrastructure.external;

import com.team.logger.stereotype.Client;
import com.team.orderservice.view.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Client
public class ProductServiceClient {
  private final WebClient client;

  @Autowired
  public ProductServiceClient(@Qualifier("productServiceWebClientBuilder") WebClient.Builder client) {
    this.client = client.build();
  }

  public ProductDto.Response.Common get(Long id) {
    return client.get()
      .uri("/api/products/" + id)
      .exchangeToMono(resp -> {
        if (resp.statusCode().is4xxClientError()) {
          return resp.bodyToMono(String.class).flatMap(body -> {
            throw new IllegalArgumentException(body);
          });
        } else if (resp.statusCode().is5xxServerError()) {
          return resp.bodyToMono(String.class).flatMap(body -> {
            throw new RuntimeException(body);
          });
        } else {
          return resp.bodyToMono(ProductDto.Response.Common.class);
        }
      })
      .block();
  }

  public List<ProductDto.Response.Common> getAll(List<Long> ids) {
    List<ProductDto.Response.Common> products = new ArrayList<>();
    for (Long id : ids) {
      products.add(get(id));
    }
    return products;
  }
}
