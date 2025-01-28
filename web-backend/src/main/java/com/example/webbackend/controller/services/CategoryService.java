package com.example.webbackend.controller.services;

import com.example.webbackend.repository.CategoryRepository;
import com.example.webbackend.repository.entity.Category;
import com.example.webbackend.repository.entity.dtos.CategoryDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = new ModelMapper();
    }

    @Cacheable(value = "allCategories")
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @CacheEvict(value = { "allCategories", "categoryByName" }, allEntries = true)
    public CategoryDto addOrUpdateCategory(String categoryName) {
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(categoryName);
        if (existingCategory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category '" + categoryName + "' already exists");
        }
        Category newCategory = new Category(categoryName, 0);
        categoryRepository.save(newCategory);
        return modelMapper.map(newCategory, CategoryDto.class);
    }

    @Cacheable(value = "categoryByName", key = "#categoryName")
    public Category getCategory(String categoryName) {
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(categoryName);
        if (existingCategory.isPresent()) {
            return modelMapper.map(existingCategory.get(), Category.class);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category '" + categoryName + "' not found");
    }

}
