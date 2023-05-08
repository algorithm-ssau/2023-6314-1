package com.team.productservice.startup;

import com.team.productservice.model.Category;
import lombok.Getter;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
public enum SetupCategory {
  ALL("All"),
  MALE("Male", Set.of(ALL)),
  FEMALE("Female", Set.of(ALL)),
  ADULT("Adult", Set.of(MALE, FEMALE)),
  CHILDREN("Children", Set.of(MALE, FEMALE)),
  ACCESSORIES("Accessories", Set.of(ADULT, CHILDREN)),
  OUTERWEAR("Outerwear", Set.of(ADULT, CHILDREN)),
  T_SHIRTS("T-shirts", Set.of(ADULT, CHILDREN)),
  DRESSES("Dresses", Set.of(ADULT, CHILDREN)),
  TROUSERS("Trousers", Set.of(ADULT, CHILDREN)),
  SHOES("Shoes", Set.of(ADULT, CHILDREN));

  private static final Category root = new Category("All", null, new HashSet<>());

  static {
    buildCategoryTree(ALL, root);
  }

  private static void buildCategoryTree(SetupCategory category, Category dst) {
    Set<SetupCategory> subs = category.getSubCategories();

    if (subs.size() == 0) return;

    subs.forEach(c -> {
      Category nextDst = dst.addSub(c.getName());
      buildCategoryTree(c, nextDst);
    });
  }
  
  private final String name;
  private final Map<String, SetupCategory> parentCategories = new HashMap<>();
  private final Set<SetupCategory> subCategories = new HashSet<>();

  SetupCategory(String name) {
    this.name = name;
  }

  SetupCategory(String name, Set<SetupCategory> parentCategories) {
    this.name = name;
    this.parentCategories.putAll(parentCategories.stream().
      collect(Collectors.toMap(SetupCategory::getName, c -> c)));
    this.parentCategories.values().forEach(pc -> pc.subCategories.add(this));
  }

  /**
   * @param path - Category path. Example: All.Male.Adult
   * @return Category
   */
  public static Category by(String path) {
    List<String> split = new ArrayList<>(Arrays.asList(path.split("\\.")));
    split.remove(0);
    return findByPath(split, root);
  }

  private static Category findByPath(List<String> path, Category nextCategory) {
    if (path.size() == 0) return nextCategory;

    Category matchCategory = nextCategory.getSubCategories().stream()
      .filter(sc -> sc.getName().equals(path.get(0)))
      .findAny()
      .orElseThrow(invalidPathExceptionSupplier(path.get(0)));
    path.remove(0);

    return findByPath(path, matchCategory);
  }

  private static Supplier<? extends RuntimeException> invalidPathExceptionSupplier(String invalidPath) {
    String message = "Invalid path, not found category with name: " + invalidPath;
    return () -> new IllegalArgumentException(message);
  }
}