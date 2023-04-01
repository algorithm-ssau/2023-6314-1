package com.team.productservice.rest.server;

import com.team.productservice.data.Product;
import com.team.productservice.mapper.ProductMapper;
import com.team.productservice.rest.client.ImageServiceClient;
import com.team.productservice.rest.client.dto.ImageRequestDto;
import com.team.productservice.service.api.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.team.productservice.dto.ProductDto.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products/")
public class ProductController {
  private final ProductService productService;
  private final ProductMapper.Request.Common reqCommonMapper;
  private final ProductMapper.Request.Create reqCreateMapper;
  private final ProductMapper.Response.Common respCommonMapper;
  private final ImageServiceClient imageServiceClient;

  @GetMapping
  public ResponseEntity<List<Response.Common>> getAll() {
    var responses = productService.getAll().stream()
      .map(respCommonMapper::toDto)
      .toList();
    return ResponseEntity.ok().body(responses);
  }

  @GetMapping("{id}")
  public ResponseEntity<Response.Common> get(@PathVariable Long id) {
    Product product = productService.getById(id);
    var common = respCommonMapper.toDto(product);
    return ResponseEntity.ok().body(common);
  }

  @PostMapping
  public ResponseEntity<Response.Common> create(@Valid @RequestBody Request.Create dto) {
    List<ImageRequestDto> imageRequestDtos = Arrays.stream(dto.getImagesContent())
      .map(ImageRequestDto::new)
      .toList();
    List<Long> imagesId = imageServiceClient.saveAll(imageRequestDtos);
    Product product = reqCreateMapper.toDomain(dto, imagesId);
    productService.save(product);
    return ResponseEntity.ok().build();
  }

  @PutMapping("{id}")
  public ResponseEntity<Response.Common> update(
    @PathVariable Long id,
    @Valid @RequestBody Request.Common productRequestDto
  ) {
    Product product = reqCommonMapper.toDomain(productRequestDto);
    product.setId(id);
    productService.update(product);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Response.Common> delete(@PathVariable Long id) {
    productService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}