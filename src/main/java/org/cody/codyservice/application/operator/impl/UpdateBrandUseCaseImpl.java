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
        // Optional 대신 직접 null 체크
        Brand existingBrand = brandRepository.findById(id);
        if (existingBrand == null) {
            throw new BusinessException("브랜드를 찾을 수 없습니다: " + id);
        }
        
        // 업데이트할 필드 설정
        existingBrand.setName(updatedBrand.getName());
        
        // 수정 시간 업데이트
        existingBrand.setUpdatedAt(LocalDateTime.now());
        
        return brandRepository.save(existingBrand);
    }
} 