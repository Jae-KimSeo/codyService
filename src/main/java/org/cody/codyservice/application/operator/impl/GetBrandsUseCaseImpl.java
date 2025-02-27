package org.cody.codyservice.application.operator.impl;

import java.util.List;

import org.cody.codyservice.application.operator.GetBrandsUseCase;
import org.cody.codyservice.common.exception.BusinessException;
import org.cody.codyservice.domain.operator.Brand;
import org.cody.codyservice.domain.operator.repository.BrandRepository;
import org.springframework.stereotype.Service;

@Service
public class GetBrandsUseCaseImpl implements GetBrandsUseCase {

    private final BrandRepository brandRepository;
    
    public GetBrandsUseCaseImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    
    @Override
    public Brand getBrandById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("브랜드 ID는 필수입니다.");
        }
        
        Brand brand = brandRepository.findById(id);
        if (brand == null) {
            throw new BusinessException("브랜드를 찾을 수 없습니다: " + id);
        }
        return brand;
    }
    
    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
} 