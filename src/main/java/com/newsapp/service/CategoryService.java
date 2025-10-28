package com.newsapp.service;

import com.newsapp.dto.CategoryDTO;
import com.newsapp.dto.CategoryRequest;
import com.newsapp.model.Category;
import com.newsapp.model.User;
import com.newsapp.repository.CategoryRepository;
import com.newsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public Page<CategoryDTO> getAllCategories(Pageable pageable, String search) {
        Page<Category> categories;

        if (search != null && !search.trim().isEmpty()) {
            categories = categoryRepository.searchCategories(search.trim(), pageable);
        } else {
            categories = categoryRepository.findAll(pageable);
        }

        return categories.map(this::convertToDto);
    }

    @Transactional
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return convertToDto(category);
    }

    @Transactional
    public CategoryDTO createCategory(CategoryRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setCreatedBy(currentUser);

        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setUpdatedBy(currentUser);

        Category updatedCategory = categoryRepository.save(category);
        return convertToDto(updatedCategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }

    private CategoryDTO convertToDto(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        dto.setCreatedByName(category.getCreatedBy() != null ? category.getCreatedBy().getName() : null);
        dto.setUpdatedByName(category.getUpdatedBy() != null ? category.getUpdatedBy().getName() : null);
        return dto;
    }

}
