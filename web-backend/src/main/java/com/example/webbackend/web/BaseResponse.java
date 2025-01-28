package com.example.webbackend.web;

import com.example.webbackend.repository.entity.dtos.Dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse<T> {
    @JsonProperty("responseHeader")
    private ResponseHeader responseHeader;
    @JsonProperty("dto")
    private Dto dto;
    public BaseResponse(ResponseHeader responseHeader, Dto dto) {
        this.responseHeader = responseHeader;
        this.dto = dto;
    }

    public BaseResponse() {
    }
}
