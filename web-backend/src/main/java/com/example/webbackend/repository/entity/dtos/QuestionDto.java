package com.example.webbackend.repository.entity.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionDto extends Dto{
    @JsonProperty("id")
    private Long id;
    @JsonProperty("designer")
    private String designer;
    @JsonProperty("question")
    private String question;

    @JsonProperty("answer1")
    private String answer1;

    @JsonProperty("answer2")
    private String answer2;

    @JsonProperty("answer3")
    private String answer3;

    @JsonProperty("answer4")
    private String answer4;

    @JsonProperty("category")
    private String category;

    @JsonProperty("hardness")
    private String hardness;

    @JsonProperty("correct_answer")
    private String correct_answer;
}
