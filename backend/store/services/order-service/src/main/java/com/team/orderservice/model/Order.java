package com.team.orderservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "orders")
@Where(clause = "arrival_date_time > now()")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "orders_sequence", allocationSize = 20)
  @Column(name = "id")
  private Long id;

  @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Address arrivalAddress;

  @ElementCollection
  @Column(name = "products")
  private List<Long> products;

  @PositiveOrZero
  @Column(name = "user_id")
  private Long userId;

  @PastOrPresent
  @Column(name = "payload_date_time", columnDefinition = "timestamp with time zone")
  private OffsetDateTime payloadDateTime = OffsetDateTime.now();

  @FutureOrPresent
  @Column(name = "arrival_date_time", columnDefinition = "timestamp with time zone")
  private OffsetDateTime arrivalDateTime;

  @Enumerated(EnumType.STRING)
  private Status status = Status.CREATED;

  public Order(
    Address arrivalAddress,
    List<Long> products,
    Long userId,
    OffsetDateTime payloadDateTime,
    OffsetDateTime arrivalDateTime
  ) {
    this(arrivalAddress, products, userId, arrivalDateTime);
    this.payloadDateTime = payloadDateTime;
  }

  public Order(
    Address arrivalAddress,
    List<Long> products,
    Long userId,
    OffsetDateTime arrivalDateTime
  ) {
    this.arrivalAddress = arrivalAddress;
    this.products = products;
    this.userId = userId;
    this.arrivalDateTime = arrivalDateTime;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
