package org.cody.codyservice.application.operator.impl;

import java.time.LocalDateTime;

import org.cody.codyservice.application.operator.UpdateBrandUseCase;
import org.cody.codyservice.common.exception.BusinessException;
import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateBrandUseCaseImpl implements UpdateBrandUseCase {

    private final BrandRepository brandRepository;
    
    public UpdateBrandUseCaseImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    
    @Override
    public Brand updateBrand(Integer id, Brand updatedBrand) {
        Brand existingBrand = brandRepository.findById(id);
        if (existingBrand == null) {
            throw new BusinessException("브랜드를 찾을 수 없습니다: " + id);
        }
        
        existingBrand.setName(updatedBrand.getName());
        
        existingBrand.setUpdatedAt(LocalDateTime.now());
        
        return brandRepository.save(existingBrand);
    }
} 