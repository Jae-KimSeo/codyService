package org.cody.codyservice.application.operator;

import org.cody.codyservice.domain.operator.Brand;

public interface CreateBrandUseCase {
    /**
     * 새로운 브랜드를 생성합니다.
     * @param brand 생성할 브랜드 정보
     * @return 생성된 브랜드
     */
    Brand createBrand(Brand brand);
} 