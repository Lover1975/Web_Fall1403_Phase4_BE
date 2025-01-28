package com.example.webbackend.repository.entity.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IntegerDto extends Dto {
    @JsonProperty("value")
    private Integer value;
}
