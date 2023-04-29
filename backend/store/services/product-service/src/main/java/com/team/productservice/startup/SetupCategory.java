package com.team.productservice.startup;

import java.util.Set;

public enum SetupCategory {
  MALE("Male", null, null),
  FEMALE("Female", null, null);

  private final String name;
  private final SetupCategory parentCategory;
  private final Set<SetupCategory> subCategories;

  SetupCategory(String name, SetupCategory parentCategory, Set<SetupCategory> subCategories) {
    this.name = name;
    this.parentCategory = parentCategory;
    this.subCategories = subCategories;
  }
}
