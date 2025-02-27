package org.cody.codyservice.application.cody.response;

import java.math.BigDecimal;

import org.cody.codyservice.domain.cody.ProductView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPriceRangeResponse {
    private String categoryName;
    private BigDecimal lowestPrice;
    private BigDecimal highestPrice;
    private String lowestPriceBrandName;
    private String highestPriceBrandName;
    private ProductView lowestPriceProductView;
    private ProductView highestPriceProductView;
    private BigDecimal averagePrice;
    private int productCount;
} 