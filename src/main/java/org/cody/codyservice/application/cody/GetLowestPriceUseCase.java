package org.cody.codyservice.application.cody;

import org.cody.codyservice.application.cody.response.CategoryPriceResponse;

public interface GetLowestPriceUseCase {
    CategoryPriceResponse getCategoryLowestPriceInfo(Integer categoryId);
} 