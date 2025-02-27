package org.cody.codyservice.application.cody.response;

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
    private Integer categoryId;
    private ProductView lowestPriceProductView;
    private ProductView highestPriceProductView;
} 