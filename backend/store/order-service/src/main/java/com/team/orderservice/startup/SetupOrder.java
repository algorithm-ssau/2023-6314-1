package com.team.orderservice.startup;

import com.team.orderservice.data.Address;
import com.team.orderservice.data.Coordinate;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
public enum SetupOrder {
  ORDER_1(
    new Address(
      "1575 Evergreen Ave",
      "Juneau",
      "Alaska (AK)",
      "USA",
      99801,
      new Coordinate(58.30603649973212, -134.42328215777576)
    ),
    List.of(1L, 2L, 3L),
    OffsetDateTime.now().minusMonths(2),
    OffsetDateTime.now().plusMonths(1)
  ),
  ORDER_2(
    new Address(
      "шоссе Гагарина, 19",
      "город Воскресенск",
      "Нижегородская область",
      "Россия",
      863005,
      new Coordinate(83.298754, 32.828425)
    ),
    List.of(4L),
    OffsetDateTime.now().minusMonths(3),
    OffsetDateTime.now().plusMonths(1)
  ),
  ORDER_3(
    new Address(
      "наб. Чехова, 10",
      "город Видное",
      "Курганская область",
      "Россия",
      878634,
      new Coordinate(-12.675799, -55.407463)
    ),
    List.of(1L, 3L),
    OffsetDateTime.now().minusMonths(4),
    OffsetDateTime.now().plusMonths(1)
  ),
  ORDER_4(
    new Address(
      "пер. Сталина, 84",
      "город Мытищи",
      "Иркутская область",
      "Россия",
      902437,
      new Coordinate(-26.34422, 44.281123)
    ),
    List.of(2L, 4L),
    OffsetDateTime.now().minusMonths(2),
    OffsetDateTime.now().plusMonths(1)
  ),
  ORDER_5(
    new Address(
      "886 W 5600th S",
      "Preston",
      "Idaho (ID)",
      "USA",
      83263,
      new Coordinate(41.99900968774081, -111.8976923637181)
    ),
    List.of(1L, 2L, 3L, 4L),
    OffsetDateTime.now().minusMonths(3),
    OffsetDateTime.now().plusMonths(2)
  );

  private final Address arrivalAddress;
  private final List<Long> products;
  private final OffsetDateTime payloadDateTime;
  private final OffsetDateTime arrivalDateTime;
  private final OffsetDateTime timestamp = OffsetDateTime.now();

  SetupOrder(Address arrivalAddress,
             List<Long> products,
             OffsetDateTime payloadDateTime,
             OffsetDateTime arrivalDateTime) {
    this.arrivalAddress = arrivalAddress;
    this.products = products;
    this.payloadDateTime = payloadDateTime;
    this.arrivalDateTime = arrivalDateTime;
  }
}
