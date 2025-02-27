package org.cody.codyservice.application.operator.impl;

import java.time.LocalDateTime;

import org.cody.codyservice.application.operator.CreateBrandUseCase;
import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateBrandUseCaseImpl implements CreateBrandUseCase {

    private final BrandRepository brandRepository;
    
    public CreateBrandUseCaseImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    
    @Override
    public Brand createBrand(Brand brand) {
        if (brand.getName() == null || brand.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("브랜드 이름은 필수입니다.");
        }
        
        if (brand.getCreatedAt() == null) {
            brand.setCreatedAt(LocalDateTime.now());
        }
        
        if (brand.getUpdatedAt() == null) {
            brand.setUpdatedAt(LocalDateTime.now());
        }
        
        return brandRepository.save(brand);
    }
} 