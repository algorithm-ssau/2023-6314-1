package com.team.productservice.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(nullable = false)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String name;

  @NotBlank
  @Column(columnDefinition = "text")
  private String description;

  @Positive
  @Column(nullable = false)
  private BigDecimal cost;

  @PositiveOrZero
  @Column(nullable = false)
  private Long countInStock;

  @NotNull
  @ElementCollection
  @JoinColumn(name = "product", nullable = false)
  private List<Long> imageIds;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "category")
  private Category category;
}
