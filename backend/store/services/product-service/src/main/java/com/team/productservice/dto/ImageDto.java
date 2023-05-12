package com.team.productservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

public enum ImageDto {;
  private interface Id { @NotNull Long getId(); }
  private interface Content { @Size(max = 100 * 1024 * 1024) byte[] getContent(); }

  public enum Request {;
    @Value
    public static class Common implements Content {
      byte[] content;

      @JsonCreator
      public Common(@JsonProperty("content") byte[] content) {
        this.content = content;
      }
    }
  }

  public enum Response {;
    @Value
    public static class Common implements Id, Content{
      Long id;
      byte[] content;

      @JsonCreator
      public Common(
        @JsonProperty("id") Long id,
        @JsonProperty("content") byte[] content
      ) {
        this.id = id;
        this.content = content;
      }
    }
  }
}

