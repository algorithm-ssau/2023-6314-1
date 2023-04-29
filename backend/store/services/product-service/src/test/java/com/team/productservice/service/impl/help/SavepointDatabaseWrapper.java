package com.team.productservice.service.impl.help;

import com.team.productservice.data.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SavepointDatabaseWrapper implements DatabaseWrapper<Category, Long> {
  private long nextId = 0;
  private final List<Category> database = new ArrayList<>();

  private Category generateCategoryById() {
    return Category.builder()
      .id(nextId)
      .name("Category" + nextId++)
      .build();
  }

  @Override
  public Category add(Category category) {
    category.setId(nextId++);
    database.add(category);
    return category;
  }

  @Override
  public Category findById(Long id) {
    try {
      return database.get(wrapId(id));
    } catch (IndexOutOfBoundsException ex) {
      throw new IllegalArgumentException(ex);
    }
  }

  @Override
  public Long size() {
    return (long) database.size();
  }

  @Override
  public Category deleteById(Long id) {
    return database.remove((int) (long) id);
  }

  @Override
  public Set<Category> projection(Set<Long> longs) {
    return database.stream()
      .filter(c -> longs.contains(c.getId()))
      .collect(Collectors.toSet());
  }

  public void toSavePoint() {
    nextId = 0;
    database.clear();
    database.addAll(Stream.generate(this::generateCategoryById).limit(10).toList());
  }

  private int wrapId(Long id) {
    int intId = (int) (long) id;
    if (((long) intId) < id) {
      throw new IllegalArgumentException("Test id must be less than long");
    }
    return intId;
  }
}
