package com.Solvro.Cocktailer.mapper;

import com.Solvro.Cocktailer.DTO.IngredientDto;
import com.Solvro.Cocktailer.model.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    @Named("mapToDto")
    IngredientDto mapToDto(Ingredient ingredient);

    @Named("mapToEntity")
    @Mapping(target = "id",ignore = true)
    Ingredient mapToEntity(IngredientDto dto);
}
