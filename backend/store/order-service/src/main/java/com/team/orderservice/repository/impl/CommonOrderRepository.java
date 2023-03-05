package com.team.orderservice.repository.impl;

import com.team.orderservice.data.Order;
import com.team.orderservice.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Repository
@RequiredArgsConstructor
@Transactional
public class CommonOrderRepository implements OrderRepository {
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Order save(Order order) {
    entityManager.persist(order);
    return order;
  }

  @Override
  public Long count() {
    String query = "select count(o) from Order o where o.arrivalDateTimeMinutes > :now";
    return entityManager.createQuery(query, Long.class)
      .setParameter("now", OffsetDateTime.now().toInstant().getEpochSecond() / 60)
      .getSingleResult();
  }
}
