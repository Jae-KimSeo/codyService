package org.cody.codyservice.domain.operator.repository;

import java.util.List;

import org.cody.codyservice.domain.operator.Category;

public interface CategoryRepository {
    Category findById(Integer categoryId);
    List<Category> findAll();
} 