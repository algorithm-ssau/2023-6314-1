package com.team.productservice.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product {
  @Id
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @Positive
  private Integer cost;

  @Positive
  private Long countInStock;

  @NotNull
  @JoinColumn(name = "product_id")
  @OneToMany(fetch = FetchType.LAZY)
  private List<Image> images;
}
