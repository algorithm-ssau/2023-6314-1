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
  private interface CategoryId { @NotNull Long getCategoryId(); }
  private interface CategoryRequest { @NotNull CategoryDto.Request.Common getCategory(); }
  private interface CategoryResponse { @NotNull CategoryDto.Response.Common getCategory(); }

  public enum Request {;
    @Value
    public static class Common implements
      Name, Description, Cost, CountInStock, ImagesContent, CategoryRequest
    {
      String name;
      String description;
      BigDecimal cost;
      Long countInStock;
      List<String> imagesContent;
      CategoryDto.Request.Common category;

      @JsonCreator
      public Common(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("cost") BigDecimal cost,
        @JsonProperty("countInStock") Long countInStock,
        @JsonProperty("imagesContent") List<String> imagesContent,
        @JsonProperty("category") CategoryDto.Request.Common category
      ) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.countInStock = countInStock;
        this.imagesContent = imagesContent;
        this.category = category;
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
    public static class Create implements Name, Description, Cost, CountInStock, ImagesBytes, CategoryId {
      String name;
      String description;
      BigDecimal cost;
      Long countInStock;
      byte[][] imagesBytes;
      Long categoryId;

      @JsonCreator
      public Create(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("cost") BigDecimal cost,
        @JsonProperty("countInStock") Long countInStock,
        @JsonProperty("imagesBytes") byte[][] imagesBytes,
        @JsonProperty("categoryId") Long categoryId
      ) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.countInStock = countInStock;
        this.imagesBytes = imagesBytes;
        this.categoryId = categoryId;
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
    public static class Update implements Name, Description, Cost, CountInStock, ImagesBytes, CategoryRequest {
      String name;
      String description;
      BigDecimal cost;
      Long countInStock;
      byte[][] imagesBytes;
      CategoryDto.Request.Common category;

      @JsonCreator
      public Update(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("cost") BigDecimal cost,
        @JsonProperty("countInStock") Long countInStock,
        @JsonProperty("imagesBytes") byte[][] imagesBytes,
        @JsonProperty("category") CategoryDto.Request.Common category
      ) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.countInStock = countInStock;
        this.imagesBytes = imagesBytes;
        this.category = category;
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
    public static class Common implements Id, Name, Description, Cost, CountInStock, ImagesContent, CategoryResponse {
      Long id;
      String name;
      String description;
      BigDecimal cost;
      Long countInStock;
      List<String> imagesContent;
      CategoryDto.Response.Common category;

      @JsonCreator
      public Common(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("cost") BigDecimal cost,
        @JsonProperty("countInStock") Long countInStock,
        @JsonProperty("imagesContent") List<String> imagesContent,
        @JsonProperty("category") CategoryDto.Response.Common category
      ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.countInStock = countInStock;
        this.imagesContent = imagesContent;
        this.category = category;
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
