package org.cody.codyservice.adapter.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cody.codyservice.config.CategoryProperties;
import org.cody.codyservice.domain.operator.Category;
import org.cody.codyservice.domain.operator.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    
    private final Map<Integer, Category> categories = new HashMap<>();
    private final CategoryProperties categoryProperties;
    
    public CategoryRepositoryImpl(CategoryProperties categoryProperties) {
        this.categoryProperties = categoryProperties;
    }

    @PostConstruct
    public void init() {
        Map<Integer, String> categoryMap = categoryProperties.getCategoryMap();
        
        if (categoryMap.isEmpty()) {
            // 설정 파일에 데이터가 없는 경우 기본값 사용
            addDefaultCategory(1, "상의");
            addDefaultCategory(2, "아우터");
            addDefaultCategory(3, "바지");
            addDefaultCategory(4, "스니커즈");
            addDefaultCategory(5, "가방");
            addDefaultCategory(6, "모자");
            addDefaultCategory(7, "양말");
            addDefaultCategory(8, "액세서리");
        } else {
            for (Map.Entry<Integer, String> entry : categoryMap.entrySet()) {
                addDefaultCategory(entry.getKey(), entry.getValue());
            }
        }
    }
    
    private void addDefaultCategory(Integer id, String name) {
        categories.put(id, new Category(id, name));
    }
    
    @Override
    public Category findById(Integer categoryId) {
        return categories.get(categoryId);
    }
    
    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

} 