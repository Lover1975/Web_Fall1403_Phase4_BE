package com.example.webbackend.web;

import com.example.webbackend.repository.entity.dtos.Dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse<T> { // Add a generic type parameter T
    @JsonProperty("responseHeader")
    private ResponseHeader responseHeader;

    @JsonProperty("dto") // Rename this field to a more general name like "data"
    private Dto dto; // Use the generic type T instead of Dto

    public BaseResponse(ResponseHeader responseHeader, Dto dto) {
        this.responseHeader = responseHeader;
        this.dto = dto;
    }

    public BaseResponse() {
    }
}
