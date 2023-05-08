package com.team.orderservice.startup;

import com.team.orderservice.model.Address;
import com.team.orderservice.model.Coordinate;
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
      "99801",
      new Coordinate("58.30603649973212", "-134.42328215777576")
    ),
    List.of(1L, 2L, 3L),
    1L,
    OffsetDateTime.now().plusMonths(1)
  ),
  ORDER_2(
    new Address(
      "шоссе Гагарина, 19",
      "город Воскресенск",
      "Нижегородская область",
      "Россия",
      "863005",
      new Coordinate("83.298754", "32.828425")
    ),
    List.of(4L),
    2L,
    OffsetDateTime.now().plusMonths(1)
  ),
  ORDER_3(
    new Address(
      "наб. Чехова, 10",
      "город Видное",
      "Курганская область",
      "Россия",
      "878634",
      new Coordinate("-12.675799", "-55.407463")
    ),
    List.of(1L, 3L),
    3L,
    OffsetDateTime.now().plusMonths(1)
  ),
  ORDER_4(
    new Address(
      "пер. Сталина, 84",
      "город Мытищи",
      "Иркутская область",
      "Россия",
      "902437",
      new Coordinate("-26.34422", "44.281123")
    ),
    List.of(2L, 4L),
    3L,
    OffsetDateTime.now().plusMonths(1)
  ),
  ORDER_5(
    new Address(
      "886 W 5600th S",
      "Preston",
      "Idaho (ID)",
      "USA",
      "83263",
      new Coordinate("41.99900968774081", "-111.8976923637181")
    ),
    List.of(1L, 2L, 3L, 4L),
    2L,
    OffsetDateTime.now().plusMonths(2)
  );

  private final Address arrivalAddress;
  private final List<Long> products;
  private final Long userId;
  private final OffsetDateTime payloadDateTime = OffsetDateTime.now();
  private final OffsetDateTime arrivalDateTime;

  SetupOrder(
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
}
