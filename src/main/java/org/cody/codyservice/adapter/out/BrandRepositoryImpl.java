package org.cody.codyservice.adapter.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BrandRepositoryImpl implements BrandRepository {
    
    private final Map<Integer, Brand> brands = new HashMap<>();
    
    @Override
    public Brand findById(Integer brandId) {
        return brands.get(brandId);
    }
    
    @Override
    public List<Brand> findAll() {
        return new ArrayList<>(brands.values());
    }
    
    @Override
    public Brand save(Brand brand) {
        brands.put(brand.getBrandId(), brand);
        return brand;
    }
    
    @Override
    public void deleteById(Integer brandId) {
        brands.remove(brandId);
    }
} 