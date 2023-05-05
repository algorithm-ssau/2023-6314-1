package com.team.productservice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

public enum ProductDto {;
  private interface Id { @NotNull Long getId(); }
  private interface Name { @NotBlank String getName(); }
  private interface Description { @NotBlank String getDescription(); }
  private interface Cost { @Positive BigDecimal getCost(); }
  private interface CountInStock { @Positive Long getCountInStock(); }
  private interface ImagesContent { @NotNull List<String> getImagesContent(); }
  private interface ImagesBytes { @NotNull byte[][] getImagesBytes(); }

  public enum Request {;
    @Value
    public static class Common implements Name, Description, Cost, CountInStock, ImagesContent {
      String name;
      String description;
      BigDecimal cost;
      Long countInStock;
      List<String> imagesContent;

      @JsonCreator
      public Common(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("cost") BigDecimal cost,
        @JsonProperty("countInStock") Long countInStock,
        @JsonProperty("imagesContent") List<String> imagesContent
      ) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.countInStock = countInStock;
        this.imagesContent = imagesContent;
      }

      @Override
      public String toString() {
        return "Common{" +
          "name='" + name + '\'' +
          ", description='" + description + '\'' +
          ", cost=" + cost +
          ", countInStock=" + countInStock +
          ", imagesContentSize=" + imagesContent.size() +
          '}';
      }
    }

    @Value
    public static class Create implements Name, Description, Cost, CountInStock, ImagesBytes {
      String name;
      String description;
      BigDecimal cost;
      Long countInStock;
      byte[][] imagesBytes;

      @JsonCreator
      public Create(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("cost") BigDecimal cost,
        @JsonProperty("countInStock") Long countInStock,
        @JsonProperty("imagesBytes") byte[][] imagesBytes
      ) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.countInStock = countInStock;
        this.imagesBytes = imagesBytes;
      }

      @Override
      public String toString() {
        return "Create{" +
          "name='" + name + '\'' +
          ", description='" + description + '\'' +
          ", cost=" + cost +
          ", countInStock=" + countInStock +
          ", imagesBytesSize=" + imagesBytes.length +
          '}';
      }
    }

    @Value
    public static class Update implements Name, Description, Cost, CountInStock, ImagesBytes {
      String name;
      String description;
      BigDecimal cost;
      Long countInStock;
      byte[][] imagesBytes;

      @JsonCreator
      public Update(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("cost") BigDecimal cost,
        @JsonProperty("countInStock") Long countInStock,
        @JsonProperty("imagesBytes") byte[][] imagesBytes
      ) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.countInStock = countInStock;
        this.imagesBytes = imagesBytes;
      }

      @Override
      public String toString() {
        return "Update{" +
          "name='" + name + '\'' +
          ", description='" + description + '\'' +
          ", cost=" + cost +
          ", countInStock=" + countInStock +
          ", imagesBytesSize=" + imagesBytes.length +
          '}';
      }
    }
  }

  public enum Response {;
    @Value
    public static class Common implements Id, Name, Description, Cost, CountInStock, ImagesContent {
      Long id;
      String name;
      String description;
      BigDecimal cost;
      Long countInStock;
      List<String> imagesContent;

      @JsonCreator
      public Common(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("cost") BigDecimal cost,
        @JsonProperty("countInStock") Long countInStock,
        @JsonProperty("imagesContent") List<String> imagesContent
      ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.countInStock = countInStock;
        this.imagesContent = imagesContent;
      }

      @Override
      public String toString() {
        return "Common{" +
          "id=" + id +
          ", name='" + name + '\'' +
          ", description='" + description + '\'' +
          ", cost=" + cost +
          ", countInStock=" + countInStock +
          ", imagesContentSize=" + imagesContent.size() +
          '}';
      }
    }
  }
}
