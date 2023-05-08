package com.team.orderservice.infrastructure.external.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class RestClientsConfig {
  @Bean
  public WebClient.Builder userServiceWebClientBuilder(HttpClient httpClient) {
    return WebClient.builder()
      .clientConnector(new ReactorClientHttpConnector(httpClient))
      .baseUrl("http://user-service:8002")
      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
  }

  @Bean
  public WebClient.Builder productServiceWebClientBuilder(HttpClient httpClient) {
    final int size = 16 * 1024 * 1024;
    final ExchangeStrategies strategies = ExchangeStrategies.builder()
      .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
      .build();
    return WebClient.builder()
      .clientConnector(new ReactorClientHttpConnector(httpClient))
      .baseUrl("http://product-service:8001")
      .exchangeStrategies(strategies)
      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
  }

  @Bean
  public HttpClient httpClient() {
    return HttpClient.create()
      .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
      .responseTimeout(Duration.ofMillis(5000))
      .doOnConnected(connection -> {
        connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
        connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
      });
  }
}
