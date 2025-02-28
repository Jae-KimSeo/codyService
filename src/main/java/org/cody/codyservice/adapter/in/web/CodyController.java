package org.cody.codyservice.adapter.in.web;

import org.cody.codyservice.application.cody.GetBrandPriceStatsUseCase;
import org.cody.codyservice.application.cody.GetCategoryPriceRangeUseCase;
import org.cody.codyservice.application.cody.GetLowestPriceUseCase;
import org.cody.codyservice.application.cody.response.BrandAllCategoriesResponse;
import org.cody.codyservice.application.cody.response.CategoryPriceRangeResponse;
import org.cody.codyservice.application.cody.response.CategoryPriceResponse;
import org.cody.codyservice.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cody")
public class CodyController {
    
    private final GetLowestPriceUseCase getLowestPriceUseCase;
    private final GetCategoryPriceRangeUseCase getCategoryPriceRangeUseCase;
    private final GetBrandPriceStatsUseCase getBrandPriceStatsUseCase;
    
    @Autowired
    public CodyController(GetLowestPriceUseCase getLowestPriceUseCase,
                          GetCategoryPriceRangeUseCase getCategoryPriceRangeUseCase,
                          GetBrandPriceStatsUseCase getBrandPriceStatsUseCase) {
        this.getLowestPriceUseCase = getLowestPriceUseCase;
        this.getCategoryPriceRangeUseCase = getCategoryPriceRangeUseCase;
        this.getBrandPriceStatsUseCase = getBrandPriceStatsUseCase;
    }
    
    @GetMapping("/categories/{categoryId}/lowest-price")
    public ResponseEntity<ApiResponse<CategoryPriceResponse>> getCategoryLowestPriceInfo(
            @PathVariable Integer categoryId) {
        CategoryPriceResponse response = getLowestPriceUseCase.getCategoryLowestPriceInfo(categoryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "카테고리별 최저가격 브랜드 및 상품 정보 조회 성공", response));
    }
    
    @GetMapping("/brands/{brandId}/categories")
    public ResponseEntity<ApiResponse<BrandAllCategoriesResponse>> getBrandLowestPriceForAllCategories(
            @PathVariable Integer brandId) {
        BrandAllCategoriesResponse response = getBrandPriceStatsUseCase.getBrandLowestPriceForAllCategories(brandId);
        
        return ResponseEntity.ok(new ApiResponse<>(true, "단일 브랜드의 모든 카테고리 최저가격 정보 조회 성공", response));
    }
    
    @GetMapping("/categories/{categoryId}/price-range")
    public ResponseEntity<ApiResponse<CategoryPriceRangeResponse>> getCategoryPriceRange(
            @PathVariable Integer categoryId) {
        CategoryPriceRangeResponse response = getCategoryPriceRangeUseCase.getCategoryPriceRange(categoryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "카테고리별 가격 범위 및 통계 정보 조회 성공", response));
    }
} 