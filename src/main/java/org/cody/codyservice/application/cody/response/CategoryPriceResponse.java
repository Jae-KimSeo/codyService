package org.cody.codyservice.application.cody.response;

import java.math.BigDecimal;
import java.util.ArrayList;
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
public class CategoryPriceResponse {
    private Integer categoryId;
    private String categoryName;
    private String lowestPriceBrandName;
    private BigDecimal lowestPrice;
    private ProductView lowestPriceProduct;
    private List<BrandPriceInfo> brandPriceInfos;
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandPriceInfo {
        private String brandName;
        private BigDecimal price;
    }

    public CategoryPriceResponse(Integer categoryId, String categoryName, BigDecimal lowestPrice, ProductView lowestPriceProduct) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.lowestPrice = lowestPrice;
        this.lowestPriceProduct = lowestPriceProduct;
        this.lowestPriceBrandName = lowestPriceProduct != null ? lowestPriceProduct.getBrandName() : null;
        this.brandPriceInfos = new ArrayList<>();
    }
} 