package org.cody.codyservice.domain.operator.repository;

import java.util.List;

import org.cody.codyservice.domain.operator.Brand;

public interface BrandRepository {
    Brand findById(Integer brandId);
    List<Brand> findAll();
    Brand save(Brand brand);
    void deleteById(Integer brandId);
} 