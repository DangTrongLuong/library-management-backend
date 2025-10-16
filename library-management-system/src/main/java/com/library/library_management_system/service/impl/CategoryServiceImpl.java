package com.library.library_management_system.service.impl;

import com.library.library_management_system.dto.request.CategoryRequest;
import com.library.library_management_system.dto.response.CategoryResponse;
import com.library.library_management_system.entity.Category;
import com.library.library_management_system.exception.DuplicateException;
import com.library.library_management_system.exception.NotFoundException;
import com.library.library_management_system.mapper.CategoryMapper;
import com.library.library_management_system.repository.CategoryRepository;
import com.library.library_management_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper mapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
        return mapper.toResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByTypeName(request.getTypeName()))
            throw new DuplicateException("Type name already exists!");
        if (categoryRepository.existsByShelfPosition(request.getShelfPosition()))
            throw new DuplicateException("Shelf position already exists!");

        Category saved = categoryRepository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    @Override
    public CategoryResponse updateCategory(Integer id, CategoryRequest request) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        mapper.updateEntity(existing, request);
        Category updated = categoryRepository.save(existing);
        return mapper.toResponse(updated);
    }

    @Override
    public void deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id))
            throw new NotFoundException("Category not found with id: " + id);
        categoryRepository.deleteById(id);
    }
}
