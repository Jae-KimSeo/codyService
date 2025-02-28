package org.cody.codyservice.domain.operator.repository;

import java.util.List;

import org.cody.codyservice.domain.operator.Brand;

public interface BrandRepository {
    Brand findById(Integer id);
    List<Brand> findAll();
    Brand save(Brand brand);
    void deleteById(Integer id);
} 