package org.cody.codyservice.application.cody;

import org.cody.codyservice.application.cody.response.BrandAllCategoriesResponse;

public interface GetBrandPriceStatsUseCase {
    BrandAllCategoriesResponse getBrandLowestPriceForAllCategories(Integer brandId);
} 