package com.example.webbackend.repository.entity.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto extends Dto {
    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("number_of_questions")
    private int numberOfQuestions;
}
