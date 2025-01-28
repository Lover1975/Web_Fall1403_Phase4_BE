package com.example.webbackend.repository.entity.dtos;

import lombok.Getter;
import org.modelmapper.ModelMapper;

public class ModelMapperInstance {

    @Getter
    private static final ModelMapper modelMapper = new ModelMapper();
}