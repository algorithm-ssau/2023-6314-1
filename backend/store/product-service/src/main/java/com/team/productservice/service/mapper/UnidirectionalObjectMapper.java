package com.team.productservice.service.mapper;

/**
 * @param <F> From type. From it, you can get only the Central
 * @param <C> Central type. Kernel
 * @param <T> To type. It can only be obtained from the Central
 */
public interface UnidirectionalObjectMapper<F, C, T> {
  C toCentral(F from);
  T fromCentral(C central);
}
