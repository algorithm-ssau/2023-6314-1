package com.team.productservice.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  @Column(columnDefinition = "text")
  private String description;

  @Positive
  private BigDecimal cost;

  @PositiveOrZero
  private Long countInStock;

  @NotNull
  @JoinColumn(name = "product_id")
  @ElementCollection
  private List<Long> imageIds;

  public Product(String name, String description, BigDecimal cost,
                 Long countInStock, List<Long> imageIds) {
    this.name = name;
    this.description = description;
    this.cost = cost;
    this.countInStock = countInStock;
    this.imageIds = imageIds;
  }
}
