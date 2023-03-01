package com.team.productservice.service;

import java.io.IOException;

public interface DataGenerator<T> {
  T next() throws IOException;
}
