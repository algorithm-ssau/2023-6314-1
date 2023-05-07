package com.team.productservice.service.contract;

import java.util.List;

public interface ViewService<T> {
  T view(byte[] content);
  List<T> viewList(byte[][] content);
}
