package org.cody.codyservice.adapter.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BrandRepositoryImpl implements BrandRepository {
    
    private final Map<Integer, Brand> brands = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);
    
    @Override
    public Brand findById(Integer id) {
        return brands.get(id);
    }
    
    @Override
    public List<Brand> findAll() {
        return new ArrayList<>(brands.values());
    }
    
    @Override
    public Brand save(Brand brand) {
        if (brand.getBrandId() == null) {
            brand.setBrandId(idCounter.incrementAndGet());
        }
        brands.put(brand.getBrandId(), brand);
        return brand;
    }
    
    @Override
    public void deleteById(Integer id) {
        brands.remove(id);
    }
} 