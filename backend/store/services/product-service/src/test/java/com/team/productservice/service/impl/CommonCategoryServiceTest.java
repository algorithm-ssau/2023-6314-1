package com.team.productservice.service.impl;

import com.team.productservice.model.Category;
import com.team.productservice.infrastructure.repository.CategoryRepository;
import com.team.productservice.service.impl.help.SavepointDatabaseWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommonCategoryServiceTest {
  private static final SavepointDatabaseWrapper savepointDatabaseWrapper = new SavepointDatabaseWrapper();

  @BeforeEach
  void beforeEach() {
    savepointDatabaseWrapper.toSavePoint();
  }

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private CommonCategoryService service;

  @Test
  void shouldCreateWhenMerge() {
    saveMockModifier();

    Category expected = Category.builder().build();
    Long mergeId = service.merge(expected);
    Category actual = savepointDatabaseWrapper.findById(mergeId);

    assertNotNull(actual);
    assertEquals(expected, actual);
  }

  @Test
  void shouldUpdateWhenMerge() {
    saveMockModifier();

    Category expected = savepointDatabaseWrapper.findById(0L);
    expected.setName("Update");
    Long mergeId = service.merge(expected);
    Category actual = savepointDatabaseWrapper.findById(mergeId);

    assertNotNull(actual);
    assertEquals(expected, actual);
  }

  @Test
  void shouldGetWhenFindById() {
    findByIdMockModifier();

    Long id = savepointDatabaseWrapper.size() - 1;
    Category expected = savepointDatabaseWrapper.findById(id);
    Category actual = service.findById(id);

    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowExceptionWhenFindByNotValidId() {
    findByIdMockModifier();

    Long id = savepointDatabaseWrapper.size();
    assertThrows(IllegalArgumentException.class,
      () -> savepointDatabaseWrapper.findById(id));
  }

  @Test
  void findByProjection() {
    findProjectionMockModifier();

    Set<Long> ids = Stream.iterate(0L, i -> i + 1)
      .limit(savepointDatabaseWrapper.size() / 2)
      .collect(Collectors.toSet());

    Set<Category> categories = service.findByProjection(ids);

    assertNotNull(categories);
    categories.forEach(Assertions::assertNotNull);
    assertIterableEquals(ids, categories.stream().map(Category::getId).collect(Collectors.toSet()));
  }

  @Test
  void shouldDelete() {
    deleteByIdMockModifier();
    findByIdMockModifier();
    Long id = savepointDatabaseWrapper.size() - 1;

    long expectedSize = savepointDatabaseWrapper.size() - 1;
    Category beforeDelete = savepointDatabaseWrapper.findById(id);
    Category deleted = service.deleteById(id);
    long actualSize = savepointDatabaseWrapper.size();

    assertEquals(beforeDelete, deleted);
    assertThrows(IllegalArgumentException.class, () -> savepointDatabaseWrapper.findById(id));
    assertEquals(expectedSize, actualSize);
  }

  @Test
  void shouldFindAllSubsToEnd() {
    findByIdMockModifier();

    Set<Long> allSubsToEnd = service.findAllSubsToEnd(0L);
    assertNotEquals(0, allSubsToEnd.size());
  }

  private void saveMockModifier() {
    doAnswer(invocation -> savepointDatabaseWrapper.add(invocation.getArgument(0)))
      .when(categoryRepository)
      .save(any(Category.class));
  }

  private void findByIdMockModifier() {
    doAnswer(invocation -> {
      Category byId = savepointDatabaseWrapper.findById(invocation.getArgument(0));
      return byId == null ? Optional.empty() : Optional.of(byId);
    }).when(categoryRepository).findById(anyLong());
  }

  private void findProjectionMockModifier() {
    doAnswer(invocation -> savepointDatabaseWrapper.projection(invocation.getArgument(0)))
      .when(categoryRepository)
      .findProjection(anySet());
  }

  private void deleteByIdMockModifier() {
    doAnswer(invocation -> savepointDatabaseWrapper.deleteById(invocation.getArgument(0)))
      .when(categoryRepository)
      .deleteById(anyLong());
  }


}