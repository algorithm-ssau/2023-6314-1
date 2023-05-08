package com.team.productservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "categories")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "category_id", nullable = false)
  private long id;

  @NotBlank
  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "parent")
  private Category parentCategory;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parentCategory")
  private Set<Category> subCategories = new HashSet<>();

  public Category(String name) {
    this.name = name;
  }

  public Category(String name, Category parent, Set<Category> subCategories) {
    this.name = name;

    this.parentCategory = parent;
    if (parent != null) {
      this.parentCategory.subCategories.add(this);
    }

    this.subCategories = subCategories;
    this.subCategories.forEach(sc -> sc.parentCategory = this);
  }

  public Category addSub(String name) {
    Category sub = new Category(name);
    subCategories.add(sub);
    sub.setParentCategory(this);
    return sub;
  }

  public void removeSub(Category category) {
    subCategories.remove(category);
    category.setParentCategory(null);
  }

  public Category changeSub(Category before, String afterName) {
    removeSub(before);
    return addSub(afterName);
  }

  public void moveCategory(Category parent) {
    parent.getSubCategories().remove(this);
    this.parentCategory = parent;
    parent.getSubCategories().add(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Category category = (Category) o;
    return Objects.equals(name, category.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Category{" +
      "id=" + id +
      ", name='" + name + '\'' +
      '}';
  }
}
