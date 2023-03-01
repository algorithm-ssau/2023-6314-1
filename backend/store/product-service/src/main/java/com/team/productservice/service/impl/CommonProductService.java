package com.team.productservice.service.impl;

import com.team.productservice.data.Image;
import com.team.productservice.data.Product;
import com.team.productservice.repository.ProductRepository;
import com.team.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CommonProductService implements ProductService {
  private final ProductRepository productRepository;
  private static final String RESOURCE_DIRECTORY = System.getProperty("user.dir") + "src/main/resources/static/";

  private enum TestProduct {
    BUCKWHEAT_PORRIDGE(
      "Buckwheat Porridge",
      "If you are going hunting or fishing, be sure to take Grodfood buckwheat porridge with pork. Hearty, tasty and convenient porridge with meat You will definitely like it!",
      1.99,
      10L,
      List.of(map(new File(RESOURCE_DIRECTORY + "buckwheat-porridge/0.jpg")))
    ),
    COOL_JACKET(
      "Cool Jacket",
      "Can't wait until it gets colder so you can start dressing accordingly? Then you definitely need this cool down jacket!",
      139.99,
      5L,
      List.of(
        map(new File(RESOURCE_DIRECTORY + "cool-jacket/0.jpeg")),
        map(new File(RESOURCE_DIRECTORY + "cool-jacket/1.jpeg"))
      )
    ),
    WIDE_PANTS(
      "Wide Pants",
      "Want to be wide but don't feel like wasting time in the gym? Order yourself these crisp wide panties, be in the subject, dude.",
      99.99,
      15L,
      List.of(map(new File(RESOURCE_DIRECTORY + "wide-pants/0.jpg")))
    ),
    IPHONE(
      "iPhone",
      "What to explain to you?",
      1099.99,
      20L,
      List.of(
        map(new File(RESOURCE_DIRECTORY + "iphone/0.jpeg")),
        map(new File(RESOURCE_DIRECTORY + "iphone/1.jpeg")),
        map(new File(RESOURCE_DIRECTORY + "iphone/2.jpeg")),
        map(new File(RESOURCE_DIRECTORY + "iphone/3.jpeg"))
      )
    );

    private final String name;
    private final String description;
    private final Double cost;
    private final Long countInStock;
    private final List<Image> images;

    TestProduct(String name, String description, Double cost, Long countInStock, List<Image> images) {
      this.name = name;
      this.description = description;
      this.cost = cost;
      this.countInStock = countInStock;
      this.images = images;
    }

    private static Image map(File from) {
      try {
        byte[] fromBytes = Files.readAllBytes(from.toPath());
        return new Image(fromBytes);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    public Product toValue() {
      Product product = new Product();
      product.setCost(cost);
      product.setDescription(description);
      product.setCountInStock(countInStock);
      product.setImages(images);
      product.setName(name);
      return product;
    }
  }

  @Override
  public void seedData() {
    for (TestProduct testProduct : TestProduct.values()) {
      Product product = testProduct.toValue();
      if (!productRepository.existsByName(product.getName())) {
        productRepository.save(product);
      }
    }
  }

  @Override
  public List<Product> getAll() {
    return productRepository.findAll();
  }
}