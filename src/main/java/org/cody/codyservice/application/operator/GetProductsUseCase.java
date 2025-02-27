package org.cody.codyservice.application.operator;

import java.util.List;

import org.cody.codyservice.domain.operator.Product;

public interface GetProductsUseCase {
    /**
     * 특정 ID의 상품을 조회합니다.
     * @param id 조회할 상품의 ID
     * @return 조회된 상품
     * @throws org.cody.codyservice.common.exception.BusinessException 상품이 존재하지 않을 경우
     */
    Product getProductById(Integer id);
    
    /**
     * 모든 상품을 조회합니다.
     * @return 상품 목록
     */
    List<Product> getAllProducts();
} 