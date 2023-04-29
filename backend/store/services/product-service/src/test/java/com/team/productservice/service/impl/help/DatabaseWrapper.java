package com.team.productservice.service.impl.help;

import java.util.Set;

public interface DatabaseWrapper<T, ID> {
  T add(T t);
  T findById(ID id);
  Long size();
  T deleteById(ID id);
  Set<T> projection(Set<ID> ids);
}
