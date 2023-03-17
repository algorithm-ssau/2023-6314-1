package com.team.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

public enum ProductDto {;
  private interface Id { @NotNull Long getId(); }
  private interface Name { @NotBlank String getName(); }
  private interface Description { @NotBlank String getDescription(); }
  private interface Cost { @Positive BigDecimal getCost(); }
  private interface CountInStock { @Positive Long getCountInStock(); }
  private interface ImagesId { @Size(min = 1, max = 20) List<Long> getImagesId(); }
  private interface ImagesContent { @NotNull byte[][] getImagesContent(); }

  public enum Request {;
    @Value
    public static class Common implements Name, Description, Cost, CountInStock, ImagesId {
      String name;
      String description;
      BigDecimal cost;
      Long countInStock;
      List<Long> imagesId;
    }

    @Value
    public static class Create implements Name, Description, Cost, CountInStock, ImagesContent {
      String name;
      String description;
      BigDecimal cost;
      Long countInStock;
      byte[][] imagesContent;
    }
  }

  public enum Response {;
    @Value
    public static class Common implements Id, Name, Description, Cost, CountInStock, ImagesId {
      Long id;
      String name;
      String description;
      BigDecimal cost;
      Long countInStock;
      List<Long> imagesId;
    }
  }
}
