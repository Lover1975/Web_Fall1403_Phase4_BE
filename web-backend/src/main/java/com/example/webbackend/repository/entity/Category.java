package com.example.webbackend.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Category {

    @Id
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "number_of_questions", nullable = false)
    private int numberOfQuestions;

    public Category() {}

    public Category(String categoryName, int numberOfQuestions) {
        this.categoryName = categoryName;
        this.numberOfQuestions = numberOfQuestions;
    }

}

