package com.team.imageservice.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "images")
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @NotNull
  private byte[] content;

  public Image(@NotNull byte[] content) {
    this.content = content;
  }
}