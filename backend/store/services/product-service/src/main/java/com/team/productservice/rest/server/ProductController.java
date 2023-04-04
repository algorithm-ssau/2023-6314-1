package com.team.productservice.rest.server;

import com.team.productservice.data.Product;
import com.team.productservice.mapper.ProductMapper;
import com.team.productservice.rest.client.ImageServiceClient;
import com.team.productservice.rest.client.dto.ImageDto;
import com.team.productservice.service.api.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.team.productservice.dto.ProductDto.Request;
import static com.team.productservice.dto.ProductDto.Response;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;
  private final ProductMapper.Request.Common reqCommonMapper;
  private final ProductMapper.Request.Create reqCreateMapper;
  private final ProductMapper.Response.Common respCommonMapper;
  private final ImageServiceClient imageServiceClient;

  @GetMapping
  public ResponseEntity<List<Response.Common>> getAll() {
    var responses = productService.getAll().stream()
      .map(product -> respCommonMapper.toDto(product, toBytesImagesDto(product)))
      .toList();
    return ResponseEntity.ok().body(responses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response.Common> get(@PathVariable Long id) {
    Product product = productService.getById(id);
    var common = respCommonMapper.toDto(product, obtainContents(product));
    return ResponseEntity.ok().body(common);
  }

  private List<String> obtainContents(Product product) {
    List<String> contents = imageServiceClient.getAll(product.getImageIds());
    return contents;
  }

  @PostMapping
  public ResponseEntity<Response.Common> create(@Valid @RequestBody Request.Create dto) {
    List<Long> imagesId = imageServiceClient.saveAll(dto.getImagesContent());
    Product product = reqCreateMapper.toDomain(dto, imagesId);
    productService.save(product);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Response.Common> update(@PathVariable Long id,
                                                @Valid @RequestBody Request.Common dto) {
    Product product = productService.getById(id);
    imageServiceClient.deletePack(product.getImageIds());

    List<Long> imagesId = imageServiceClient.saveAll(dto.getImagesContent());
    product = reqCommonMapper.toDomain(dto, imagesId);
    product.setId(id);

    productService.update(product);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    productService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
