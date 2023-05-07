package com.team.productservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.Set;

public enum CategoryDto {;
  private interface Id { @NotNull Long getId(); }
  private interface Name { @NotBlank String getName(); }
  private interface ParentId { @NotNull Long getParentId(); }
  private interface Subs { @NotNull Set<Long> getSubs(); }

  public enum Request {;
    @Value
    public static class Common implements Name, ParentId, Subs {
      String name;
      Long parentId;
      Set<Long> subs;

      @JsonCreator
      public Common(
        @JsonProperty("name") String name,
        @JsonProperty("parentId") Long parentId,
        @JsonProperty("subs") Set<Long> subs
      ) {
        this.name = name;
        this.parentId = parentId;
        this.subs = subs;
      }
    }
  }

  public enum Response {;
    @Value
    public static class Common implements Id, Name, ParentId, Subs {
      Long id;
      String name;
      Long parentId;
      Set<Long> subs;

      @JsonCreator
      public Common(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("parentId") Long parentId,
        @JsonProperty("subs") Set<Long> subs
      ) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.subs = subs;
      }
    }
  }
}
