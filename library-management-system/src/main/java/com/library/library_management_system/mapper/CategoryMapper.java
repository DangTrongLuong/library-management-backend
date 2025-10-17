package com.library.library_management_system.mapper;

import com.library.library_management_system.dto.request.CategoryRequest;
import com.library.library_management_system.dto.response.CategoryResponse;
import com.library.library_management_system.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest dto) {
        Category category = new Category();
        category.setTypeName(dto.getTypeName());
        category.setDescription(dto.getDescription());
        category.setShelfPosition(dto.getShelfPosition());
        category.setNote(dto.getNote());
        return category;
    }

    public CategoryResponse toResponse(Category entity) {
        return CategoryResponse.builder()
                .categoryId(entity.getCategoryId())
                .typeName(entity.getTypeName())
                .description(entity.getDescription())
                .shelfPosition(entity.getShelfPosition())
                .note(entity.getNote())
                .build();
    }

    public void updateEntity(Category category, CategoryRequest dto) {
        category.setTypeName(dto.getTypeName());
        category.setDescription(dto.getDescription());
        category.setShelfPosition(dto.getShelfPosition());
        category.setNote(dto.getNote());
    }
}

