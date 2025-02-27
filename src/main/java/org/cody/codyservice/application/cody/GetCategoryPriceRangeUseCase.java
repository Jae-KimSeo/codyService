package org.cody.codyservice.application.cody;

import org.cody.codyservice.application.cody.response.CategoryPriceRangeResponse;

public interface GetCategoryPriceRangeUseCase {
    CategoryPriceRangeResponse getCategoryPriceRange(Integer categoryId);
} 