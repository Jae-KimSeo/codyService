package org.cody.codyservice.application.operator;

public interface DeleteBrandUseCase {
    /**
     * 브랜드를 삭제합니다.
     * @param id 삭제할 브랜드 ID
     */
    void deleteBrand(Integer id);
} 