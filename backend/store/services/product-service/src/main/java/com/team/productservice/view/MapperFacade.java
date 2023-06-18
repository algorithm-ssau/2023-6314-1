package com.team.productservice.view;

import com.team.productservice.infrastructure.external.ImageServiceClient;
import com.team.productservice.model.Category;
import com.team.productservice.model.Product;
import com.team.productservice.service.contract.CategoryService;
import com.team.productservice.view.dto.CategoryDto;
import com.team.productservice.view.dto.ProductDto;
import com.team.productservice.view.mapper.Base64Mapper;
import com.team.productservice.view.mapper.CategoryMapper;
import com.team.productservice.view.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapperFacade {
  private final CategoryMapper.Request.Common commonRequestCategoryMapper;
  private final CategoryMapper.Response.Common commonResponseCategoryMapper;
  private final ProductMapper.Request.Create createRequestProductMapper;
  private final ProductMapper.Request.Update updateRequestProductMapper;
  private final ProductMapper.Response.Common commonResponseProductMapper;
  private final Base64Mapper base64Mapper;

  private final CategoryService categoryService;
  private final ImageServiceClient imageServiceClient;

  @Autowired
  public MapperFacade(CategoryMapper.Request.Common commonRequestCategoryMapper,
                      CategoryMapper.Response.Common commonResponseCategoryMapper,
                      ProductMapper.Request.Create createRequestProductMapper,
                      ProductMapper.Request.Update updateRequestProductMapper,
                      ProductMapper.Response.Common commonResponseProductMapper,
                      Base64Mapper base64Mapper,
                      CategoryService categoryService,
                      ImageServiceClient imageServiceClient) {
    this.commonRequestCategoryMapper = commonRequestCategoryMapper;
    this.commonResponseCategoryMapper = commonResponseCategoryMapper;
    this.createRequestProductMapper = createRequestProductMapper;
    this.updateRequestProductMapper = updateRequestProductMapper;
    this.commonResponseProductMapper = commonResponseProductMapper;
    this.base64Mapper = base64Mapper;
    this.categoryService = categoryService;
    this.imageServiceClient = imageServiceClient;
  }

  public Category commonRequestCategoryToDomain(CategoryDto.Request.Common dto) {
    return commonRequestCategoryMapper.toDomain(dto);
  }

  public CategoryDto.Response.Common toCommonResponseCategoryDto(Category category) {
    return commonResponseCategoryMapper.toDto(category);
  }

  public Product createRequestProductToDomain(ProductDto.Request.Create dto, List<Long> imagesIds) {
    Category category = categoryService.findById(dto.getCategoryId());
    return createRequestProductMapper.toDomain(dto, imagesIds, category);
  }

  public Product updateRequestProductToDomain(Long presentProductId,
                                              ProductDto.Request.Update dto,
                                              List<Long> imagesIds) {
    Category category = categoryService.findById(dto.getCategoryId());
    return updateRequestProductMapper.toDomain(presentProductId, dto, imagesIds, category);
  }

  public ProductDto.Response.Common toCommonResponseProductDtoWithAllImages(Product product) {
    List<String> contents = imageServiceClient.getAll(product.getImageIds());
    return commonResponseProductMapper.toDto(product, contents);
  }

  public ProductDto.Response.Common toCommonResponseProductDtoWithMainImage(Product product) {
    String content = imageServiceClient.get(product.getImageIds().get(0));
    return commonResponseProductMapper.toDto(product, content);
  }

  public String toBase64Image(byte[] content) {
    return base64Mapper.view(content);
  }

  public List<String> toBase64Images(byte[][] contents) {
    return base64Mapper.viewList(contents);
  }
}
