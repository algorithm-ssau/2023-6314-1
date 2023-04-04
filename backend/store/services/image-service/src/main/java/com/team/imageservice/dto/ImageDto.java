package com.team.imageservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

public enum ImageDto {;
  private interface Id { @NotNull Long getId(); }
  private interface Content { @NotNull String getContent(); }

  public enum Request {;
    @Value
    public static class Common implements Content {
      String content;

      @JsonCreator
      public Common(@JsonProperty("content") String content) {
        this.content = content;
      }
    }
  }

  public enum Response {;
    @Value
    public static class Common implements Id, Content{
      Long id;
      String content;

      @JsonCreator
      public Common(
        @JsonProperty("id") Long id,
        @JsonProperty("content") String content
      ) {
        this.id = id;
        this.content = content;
      }
    }
  }
}
