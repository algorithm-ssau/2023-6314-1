package com.team.productservice.rest;

import com.team.productservice.rest.dto.ImageRequestDto;
import com.team.productservice.rest.dto.ImageResponseDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "image-service", url = "http://172.24.0.9:8005/")
public interface ImageServiceClient {
  @RequestMapping(value = "api/images/{id}", method = RequestMethod.GET)
  ResponseEntity<ImageResponseDto> get(@PathVariable Long id);

  @RequestMapping(value = "api/images", method = RequestMethod.POST)
  ResponseEntity<Long> save(@Valid @RequestBody ImageRequestDto imageRequestDto);
}