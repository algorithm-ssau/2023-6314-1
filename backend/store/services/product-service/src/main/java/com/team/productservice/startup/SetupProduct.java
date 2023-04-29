package com.team.productservice.startup;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public enum SetupProduct {
  BUCKWHEAT_PORRIDGE(
    "Buckwheat Porridge",
    "If you are going hunting or fishing, be sure to take Grodfood buckwheat porridge with pork. Hearty, tasty and convenient porridge with meat You will definitely like it!",
    new BigDecimal("1.99"),
    10L,
    List.of("buckwheat-porridge/0.jpeg")
  ),
  COOL_JACKET(
    "Cool Jacket",
    "Can't wait until it gets colder so you can start dressing accordingly? Then you definitely need this cool down jacket!",
    new BigDecimal("139.99"),
    5L,
    List.of(
      "cool-jacket/0.jpeg",
      "cool-jacket/1.jpeg"
    )
  ),
  WIDE_PANTS(
    "Wide Pants",
    "Want to be wide but don't feel like wasting time in the gym? Order yourself these crisp wide panties, be in the subject, dude.",
    new BigDecimal("99.99"),
    15L,
    List.of("wide-pants/0.jpeg")
  ),
  IPHONE(
    "iPhone",
    "What to explain to you?",
    new BigDecimal("1099.99"),
    20L,
    List.of(
      "iphone/0.jpeg",
      "iphone/1.jpeg",
      "iphone/2.jpeg",
      "iphone/3.jpeg"
    )
  );

  private final String imageDir = "/images";
  private final String name;
  private final String description;
  private final BigDecimal cost;
  private final Long countInStock;
  private final List<String> imagePaths;

  SetupProduct(String name,
               String description,
               BigDecimal cost,
               Long countInStock,
               List<String> imagePaths) {
    this.name = name;
    this.description = description;
    this.cost = cost;
    this.countInStock = countInStock;
    this.imagePaths = imagePaths.stream().map(path -> imageDir + "/" + path).toList();
  }
}