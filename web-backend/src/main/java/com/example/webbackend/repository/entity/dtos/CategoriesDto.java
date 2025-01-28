package com.example.webbackend.repository.entity.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesDto extends Dto {
    @JsonProperty("categories")
    private List<CategoryDto> categories = new LinkedList<CategoryDto>();
}
