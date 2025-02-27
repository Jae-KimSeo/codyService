package org.cody.codyservice.adapter.in.web;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.cody.codyservice.application.cody.GetBrandPriceStatsUseCase;
import org.cody.codyservice.application.cody.GetCategoryPriceRangeUseCase;
import org.cody.codyservice.application.cody.GetLowestPriceUseCase;
import org.cody.codyservice.application.cody.response.BrandAllCategoriesResponse;
import org.cody.codyservice.application.cody.response.CategoryPriceRangeResponse;
import org.cody.codyservice.application.cody.response.CategoryPriceResponse;
import org.cody.codyservice.domain.cody.ProductView;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CodyController.class)
public class CodyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private GetLowestPriceUseCase getLowestPriceUseCase;
    
    @MockBean
    private GetCategoryPriceRangeUseCase getCategoryPriceRangeUseCase;
    
    @MockBean
    private GetBrandPriceStatsUseCase getBrandPriceStatsUseCase;
    
    @Test
    @DisplayName("카테고리의 최저가 상품을 조회할 수 있다")
    void getCategoryLowestPrice() throws Exception {
        // given
        Integer categoryId = 1;
        CategoryPriceResponse response = new CategoryPriceResponse(
            categoryId,
            "의류",
            new BigDecimal("10000"),
            new ProductView(1, "저렴한 옷", new BigDecimal("10000"), "상품 설명", "아디다스", "의류", null)
        );
        
        when(getLowestPriceUseCase.getCategoryLowestPriceInfo(categoryId)).thenReturn(response);
        
        // then
        mockMvc.perform(get("/cody/categories/{categoryId}/lowest-price", categoryId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.categoryId", is(categoryId)))
                .andExpect(jsonPath("$.data.categoryName", is("의류")))
                .andExpect(jsonPath("$.data.lowestPrice", is(10000)))
                .andExpect(jsonPath("$.data.lowestPriceProduct.name", is("저렴한 옷")));
    }
    
    @Test
    @DisplayName("카테고리의 가격 범위를 조회할 수 있다")
    void getCategoryPriceRange() throws Exception {
        // given
        Integer categoryId = 1;
        String categoryName = "의류";
        ProductView lowestPriceProduct = new ProductView(1, "저렴한 옷", new BigDecimal("10000"), 
            "상품 설명", "브랜드A", categoryName, null);
        ProductView highestPriceProduct = new ProductView(2, "비싼 옷", new BigDecimal("50000"), 
            "상품 설명", "브랜드B", categoryName, null);
        
        CategoryPriceRangeResponse response = new CategoryPriceRangeResponse(
            categoryId,
            lowestPriceProduct,
            highestPriceProduct
        );
        
        when(getCategoryPriceRangeUseCase.getCategoryPriceRange(categoryId)).thenReturn(response);
        
        // then
        mockMvc.perform(get("/cody/categories/{categoryId}/price-range", categoryId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.categoryId", is(categoryId)))
                .andExpect(jsonPath("$.data.lowestPriceProductView.price", is(10000)))
                .andExpect(jsonPath("$.data.lowestPriceProductView.name", is("저렴한 옷")))
                .andExpect(jsonPath("$.data.highestPriceProductView.price", is(50000)))
                .andExpect(jsonPath("$.data.highestPriceProductView.name", is("비싼 옷")));
    }
    
    @Test
    @DisplayName("브랜드의 모든 카테고리 상품 중 최저가격 제품 정보를 조회할 수 있다")
    void getBrandAllCategoriesStats() throws Exception {
        // given
        Integer brandId = 1;
        
        List<BrandAllCategoriesResponse.CategoryPriceInfo> categoryPriceInfos = Arrays.asList(
            new BrandAllCategoriesResponse.CategoryPriceInfo(1, "의류", new BigDecimal("50000"),
                new ProductView(1, "나이키 티셔츠", new BigDecimal("50000"), "상품 설명", "나이키", "의류", null), true),
            new BrandAllCategoriesResponse.CategoryPriceInfo(2, "신발", new BigDecimal("100000"),
                new ProductView(2, "나이키 신발", new BigDecimal("100000"), "상품 설명", "나이키", "신발", null), true)
        );
        
        BrandAllCategoriesResponse response = new BrandAllCategoriesResponse(
            brandId,
            "나이키",
            categoryPriceInfos
        );
        
        when(getBrandPriceStatsUseCase.getBrandLowestPriceForAllCategories(brandId)).thenReturn(response);
        
        // then
        mockMvc.perform(get("/cody/brands/{brandId}/categories", brandId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.brandId", is(brandId)))
                .andExpect(jsonPath("$.data.brandName", is("나이키")))
                .andExpect(jsonPath("$.data.categoryPriceInfos", hasSize(2)))
                .andExpect(jsonPath("$.data.categoryPriceInfos[0].categoryId", is(1)))
                .andExpect(jsonPath("$.data.categoryPriceInfos[0].categoryName", is("의류")))
                .andExpect(jsonPath("$.data.categoryPriceInfos[0].lowestPrice", is(50000)))
                .andExpect(jsonPath("$.data.categoryPriceInfos[0].hasProduct", is(true)));
    }
} 