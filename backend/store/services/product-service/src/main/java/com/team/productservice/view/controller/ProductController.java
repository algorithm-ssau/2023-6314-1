package com.team.productservice.view.controller;

import com.team.productservice.infrastructure.external.ImageServiceClient;
import com.team.productservice.model.Product;
import com.team.productservice.service.contract.ProductService;
import com.team.productservice.view.MapperFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.team.productservice.view.dto.ProductDto.Request;
import static com.team.productservice.view.dto.ProductDto.Response;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;
  private final ImageServiceClient imageServiceClient;
  private final MapperFacade mapperFacade;

  @Autowired
  public ProductController(ProductService productService,
                           ImageServiceClient imageServiceClient,
                           MapperFacade mapperFacade) {
    this.productService = productService;
    this.imageServiceClient = imageServiceClient;
    this.mapperFacade = mapperFacade;
  }

  @GetMapping
  public ResponseEntity<List<Response.Common>> getAll() {
    var responses = productService.getAll().stream()
      .map(mapperFacade::toCommonResponseProductDtoWithAllImages)
      .toList();
    return ResponseEntity.ok().body(responses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response.Common> get(@PathVariable Long id) {
    Product product = productService.getById(id);
    var common = mapperFacade.toCommonResponseProductDtoWithMainImage(product);
    return ResponseEntity.ok().body(common);
  }

  @GetMapping("/with-all-images/{id}")
  public ResponseEntity<Response.Common> getWithAllImages(@PathVariable Long id) {
    Product product = productService.getById(id);
    var common = mapperFacade.toCommonResponseProductDtoWithAllImages(product);
    return ResponseEntity.ok().body(common);
  }

  @PostMapping
  public ResponseEntity<Response.Common> create(@Valid @RequestBody Request.Create dto) {
    List<String> base64Images = mapperFacade.toBase64Images(dto.getImagesBytes());
    List<Long> savedImagesIds = imageServiceClient.saveAll(base64Images);
    Product product = mapperFacade.createRequestProductToDomain(dto, savedImagesIds);
    productService.save(product);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Response.Common> update(@PathVariable Long id,
                                                @Valid @RequestBody Request.Update dto) {
    Product product = productService.getById(id);
    imageServiceClient.deletePack(product.getImageIds());

    List<String> imagesContent = Arrays.stream(dto.getImagesBytes())
      .map(mapperFacade::toBase64Image)
      .toList();
    List<Long> imageIds = imageServiceClient.saveAll(imagesContent);
    product = mapperFacade.updateRequestProductToDomain(id, dto, imageIds);

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
    List<Response.Common> dtos = products.stream()
      .map(mapperFacade::toCommonResponseProductDtoWithMainImage)
      .toList();
    return ResponseEntity.ok(dtos);
  }
}
