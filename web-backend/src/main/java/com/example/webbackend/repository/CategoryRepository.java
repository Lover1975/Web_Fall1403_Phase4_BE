package com.example.webbackend.repository;

import com.example.webbackend.repository.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByCategoryName(String categoryName);
}
