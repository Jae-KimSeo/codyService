package org.cody.codyservice.application.operator;

import java.util.List;

import org.cody.codyservice.domain.operator.Brand;

public interface GetBrandsUseCase {
    /**
     * 특정 ID의 브랜드를 조회합니다.
     * @param id 조회할 브랜드의 ID
     * @return 조회된 브랜드
     */
    Brand getBrandById(Integer id);
    
    /**
     * 모든 브랜드를 조회합니다.
     * @return 브랜드 목록
     */
    List<Brand> getBrands();
} 