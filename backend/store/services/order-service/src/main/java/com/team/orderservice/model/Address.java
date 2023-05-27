package com.team.orderservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "orders_sequence", allocationSize = 20)
  private Long id;

  @NotBlank
  private String street;

  @NotBlank
  private String city;

  @NotBlank
  private String region;

  @NotBlank
  private String country;

  @Pattern(regexp = "^\\d{5,6}(?:[-\\s]\\d{1,4})?$")
  private String zipcode;

  @Pattern(regexp = "^-?([0-8]?[0-9]|90)(\\.[0-9]{1,40})?$")
  @Basic
  private String latitude;

  @Pattern(regexp = "^-?([0-9]{1,2}|1[0-7][0-9]|180)(\\.[0-9]{1,40})?$")
  @Basic
  private String longitude;

  @Transient
  private Coordinate coordinate;

  @PostLoad
  void fillTransient() {
    if (latitude != null && longitude != null) {
      coordinate = new Coordinate(latitude, longitude);
    }
  }

  @PrePersist
  void fillPersistent() {
    if (coordinate != null) {
      latitude = coordinate.getLatitude();
      longitude = coordinate.getLongitude();
    }
  }

  public Address(String street, String city, String region,
                 String country, String zipcode, Coordinate coordinate) {
    this.street = street;
    this.city = city;
    this.region = region;
    this.country = country;
    this.zipcode = zipcode;
    this.coordinate = coordinate;
  }
}
