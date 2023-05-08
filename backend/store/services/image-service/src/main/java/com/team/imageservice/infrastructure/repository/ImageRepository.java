package com.team.imageservice.infrastructure.repository;

import com.team.imageservice.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
