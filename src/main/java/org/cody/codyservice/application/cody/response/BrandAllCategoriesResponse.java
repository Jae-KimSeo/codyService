package org.cody.codyservice.application.cody.response;

import java.math.BigDecimal;
import java.util.List;

import org.cody.codyservice.domain.cody.ProductView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandAllCategoriesResponse {
    private Integer brandId;
    private String brandName;
    private List<CategoryPriceInfo> categoryPriceInfos;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryPriceInfo {
        private Integer categoryId;
        private String categoryName;
        private BigDecimal lowestPrice;
        private ProductView productView;
        private boolean isLowestPriceInBrand;
    }
} 