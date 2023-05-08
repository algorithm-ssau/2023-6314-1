package com.team.orderservice.infrastructure.external;

import com.team.basejwt.properties.TokenMetadata;
import com.team.logger.stereotype.Client;
import com.team.orderservice.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Client
public class ProductServiceClient {
  private final WebClient client;
  private final TokenMetadata tokenMetadata;

  @Autowired
  public ProductServiceClient(@Qualifier("productServiceWebClientBuilder") WebClient.Builder client,
                              TokenMetadata tokenMetadata) {
    this.client = client.build();
    this.tokenMetadata = tokenMetadata;
  }

  public ProductDto.Response.Common get(Long id, String token) {
    return client.get()
      .uri("/api/products/" + id)
      .header(tokenMetadata.getHeader(), "Bearer " + token)
      .retrieve()
      .bodyToMono(ProductDto.Response.Common.class)
      .block();
  }

  public List<ProductDto.Response.Common> getAll(List<Long> ids, String token) {
    List<ProductDto.Response.Common> products = new ArrayList<>();
    for (Long id : ids) {
      products.add(get(id, token));
    }
    return products;
  }
}
