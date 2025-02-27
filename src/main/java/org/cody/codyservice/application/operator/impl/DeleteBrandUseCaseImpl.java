package org.cody.codyservice.application.operator.impl;

import org.cody.codyservice.application.operator.DeleteBrandUseCase;
import org.cody.codyservice.common.exception.BusinessException;
import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteBrandUseCaseImpl implements DeleteBrandUseCase {

    private final BrandRepository brandRepository;
    
    public DeleteBrandUseCaseImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    
    @Override
    public void deleteBrand(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("브랜드 ID는 필수입니다.");
        }
        
        Brand brand = brandRepository.findById(id);
        if (brand == null) {
            throw new BusinessException("삭제할 브랜드를 찾을 수 없습니다: " + id);
        }
        
        brandRepository.deleteById(id);
    }
} 