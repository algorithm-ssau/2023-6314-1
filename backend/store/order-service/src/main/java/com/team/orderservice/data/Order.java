package com.team.orderservice.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "orders_sequence", allocationSize = 20)
  private Long id;

  @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Address arrivalAddress;

  @ElementCollection
  private List<Long> products;

  @Basic
  private long payloadDateTimeMinutes;

  @Basic
  private long arrivalDateTimeMinutes;

  @PastOrPresent
  @Transient
  private OffsetDateTime payloadDateTime = OffsetDateTime.now();

  @FutureOrPresent
  @Transient
  private OffsetDateTime arrivalDateTime;

  @Transient
  private ZoneOffset zoneOffset = payloadDateTime.getOffset();

  @PostLoad
  void fillTransient() {
    if (payloadDateTimeMinutes >= 0) {
      payloadDateTime = OffsetDateTime.ofInstant(
        Instant.ofEpochSecond(payloadDateTimeMinutes * 60),
        zoneOffset
      );
    }
    if (arrivalDateTimeMinutes >= 0) {
      arrivalDateTime = OffsetDateTime.ofInstant(
        Instant.ofEpochSecond(arrivalDateTimeMinutes * 60),
        zoneOffset
      );
    }
  }

  @PrePersist
  void fillPersistent() {
    if (payloadDateTime != null) {
      payloadDateTimeMinutes = payloadDateTime.toInstant().getEpochSecond() / 60;
    }
    if (arrivalDateTime != null) {
      arrivalDateTimeMinutes = arrivalDateTime.toInstant().getEpochSecond() / 60;
    }
  }

  public Order(Address arrivalAddress,
               List<Long> products,
               OffsetDateTime payloadDateTime,
               OffsetDateTime arrivalDateTime) {
    this.arrivalAddress = arrivalAddress;
    this.products = products;
    this.payloadDateTime = payloadDateTime;
    this.arrivalDateTime = arrivalDateTime;
  }
}
