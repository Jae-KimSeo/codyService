package org.cody.codyservice.application.operator;

import org.cody.codyservice.domain.operator.Brand;

public interface UpdateBrandUseCase {
    /**
     * 기존 브랜드 정보를 수정합니다.
     * @param id 수정할 브랜드 ID
     * @param brand 수정할 브랜드 정보
     * @return 수정된 브랜드
     */
    Brand updateBrand(Integer id, Brand brand);
} 