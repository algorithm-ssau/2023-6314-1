package com.team.productservice.service.impl.help;

import com.team.productservice.model.Category;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SavepointDatabaseWrapper implements DatabaseWrapper<Category, Long> {
  private long nextId = 0;
  private final List<Category> database = new ArrayList<>();

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

    List<Category> categories = new ArrayList<>();

    Category parent = new Category("Parent0", null, new HashSet<>());
    parent.setId(nextId++);
    categories.add(parent);

    Category category0 = new Category("Category0", parent, new HashSet<>());
    category0.setId(nextId++);
    categories.add(category0);

    Category subCategory0 = new Category("SubCategory0", category0, new HashSet<>());
    subCategory0.setId(nextId++);
    categories.add(subCategory0);

    Category subCategory1 = new Category("SubCategory1", category0, new HashSet<>());
    subCategory1.setId(nextId++);
    categories.add(subCategory1);

    Category subCategory2 = new Category("SubCategory2", category0, new HashSet<>());
    subCategory2.setId(nextId++);
    categories.add(subCategory2);

    Category category1 = new Category("Category1", parent, new HashSet<>());
    category1.setId(nextId++);
    categories.add(category1);

    Category subCategory3 = new Category("SubCategory3", category1, new HashSet<>());
    subCategory3.setId(nextId++);
    categories.add(subCategory3);

    Category subCategory4 = new Category("SubCategory4", category1, new HashSet<>());
    subCategory4.setId(nextId++);
    categories.add(subCategory4);

    Category subCategory5 = new Category("SubCategory5", category1, new HashSet<>());
    subCategory5.setId(nextId++);
    categories.add(subCategory5);

    database.addAll(categories);
  }

  private int wrapId(Long id) {
    int intId = (int) (long) id;
    if (((long) intId) < id) {
      throw new IllegalArgumentException("Test id must be less than long");
    }
    return intId;
  }
}
