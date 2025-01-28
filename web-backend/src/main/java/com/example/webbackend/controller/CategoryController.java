package com.example.webbackend.controller;

import com.example.webbackend.controller.services.CategoryService;
import com.example.webbackend.repository.entity.Category;
import com.example.webbackend.repository.entity.dtos.CategoriesDto;
import com.example.webbackend.repository.entity.dtos.CategoryDto;
import com.example.webbackend.web.BaseResponse;
import com.example.webbackend.web.ResponseHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public BaseResponse<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        CategoriesDto categoriesDto = new CategoriesDto(categories);
        return new BaseResponse<>(ResponseHeader.OK, categoriesDto);
    }

    @PostMapping("/categories")
    public BaseResponse<CategoryDto> addOrUpdateCategory(@RequestParam String categoryName) {
        CategoryDto categoryDto = categoryService.addOrUpdateCategory(categoryName);
        return new BaseResponse<>(ResponseHeader.OK, categoryDto);
    }

}
