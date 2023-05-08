package com.team.productservice.controller;

import com.team.productservice.model.Category;
import com.team.productservice.model.Product;
import com.team.productservice.infrastructure.mapper.ProductMapper;
import com.team.productservice.infrastructure.external.ImageServiceClient;
import com.team.productservice.service.contract.CategoryService;
import com.team.productservice.service.contract.ProductService;
import com.team.productservice.service.impl.Base64ViewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.team.productservice.dto.ProductDto.Request;
import static com.team.productservice.dto.ProductDto.Response;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;
  private final CategoryService categoryService;
  private final ProductMapper.Request.Create reqCreateMapper;
  private final ProductMapper.Request.Update reqUpdateMapper;
  private final ProductMapper.Response.Common respCommonMapper;
  private final ImageServiceClient imageServiceClient;
  private final Base64ViewService base64ViewService;

  @Autowired
  public ProductController(ProductService productService,
                           CategoryService categoryService,
                           ProductMapper.Request.Create reqCreateMapper,
                           ProductMapper.Response.Common respCommonMapper,
                           ProductMapper.Request.Update reqUpdateMapper,
                           ImageServiceClient imageServiceClient,
                           Base64ViewService base64ViewService) {
    this.productService = productService;
    this.categoryService = categoryService;
    this.reqCreateMapper = reqCreateMapper;
    this.respCommonMapper = respCommonMapper;
    this.reqUpdateMapper = reqUpdateMapper;
    this.imageServiceClient = imageServiceClient;
    this.base64ViewService = base64ViewService;
  }

  @GetMapping
  public ResponseEntity<List<Response.Common>> getAll() {
    var responses = productService.getAll().stream()
      .map(product -> respCommonMapper.toDto(product, obtainImages(product)))
      .toList();
    return ResponseEntity.ok().body(responses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response.Common> get(@PathVariable Long id) {
    Product product = productService.getById(id);
    var common = respCommonMapper.toDto(product, obtainMainImage(product));
    return ResponseEntity.ok().body(common);
  }

  @GetMapping("/with-all-images/{id}")
  public ResponseEntity<Response.Common> getWithAllImages(@PathVariable Long id) {
    Product product = productService.getById(id);
    var common = respCommonMapper.toDto(product, obtainImages(product));
    return ResponseEntity.ok().body(common);
  }

  private List<String> obtainImages(Product product) {
    return imageServiceClient.getAll(product.getImageIds());
  }

  private String obtainMainImage(Product product) {
    return imageServiceClient.get(product.getImageIds().get(0));
  }

  @PostMapping
  public ResponseEntity<Response.Common> create(@Valid @RequestBody Request.Create dto) {
    List<String> base64Images = base64ViewService.viewList(dto.getImagesBytes());
    List<Long> savedImagesIds = imageServiceClient.saveAll(base64Images);
    Category category = categoryService.findById(dto.getCategoryId());
    productService.save(reqCreateMapper.toDomain(dto, savedImagesIds, category));
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Response.Common> update(@PathVariable Long id,
                                                @Valid @RequestBody Request.Update dto) {
    Product product = productService.getById(id);
    imageServiceClient.deletePack(product.getImageIds());

    List<String> imagesContent = Arrays.stream(dto.getImagesBytes())
      .map(base64ViewService::view)
      .toList();
    List<Long> imagesId = imageServiceClient.saveAll(imagesContent);
    product = reqUpdateMapper.toDomain(dto, imagesId);
    product.setId(id);

    productService.update(product);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    productService.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/filter/{category-id}")
  public ResponseEntity<List<Response.Common>> findByCategory(@PathVariable("category-id") Long id) {
    List<Product> products = productService.findAllByCategoryId(id);
    List<Response.Common> dtos = products.stream().map(p -> {
      String mainImage = obtainMainImage(p);
      return respCommonMapper.toDto(p, mainImage);
    }).toList();
    return ResponseEntity.ok(dtos);
  }
}
