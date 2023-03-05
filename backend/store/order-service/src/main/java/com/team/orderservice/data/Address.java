package com.team.orderservice.data;

import jakarta.persistence.*;
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

  private String street;

  private String city;

  private String region;

  private String country;

  private Integer zipcode;

  @Basic
  private Double latitude;

  @Basic
  private Double longitude;

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
                 String country, Integer zipcode, Coordinate coordinate) {
    this.street = street;
    this.city = city;
    this.region = region;
    this.country = country;
    this.zipcode = zipcode;
    this.coordinate = coordinate;
  }
}
