package com.Solvro.Cocktailer.mapper;

import com.Solvro.Cocktailer.DTO.MeasuredIngredientDto;
import com.Solvro.Cocktailer.model.MeasuredIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface MeasuredIngredientMapper {

    @Mapping(target = "ingredientDto", source = "ingredient",qualifiedByName = "mapToDto")
    MeasuredIngredientDto mapToDto(MeasuredIngredient entity);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "ingredient", source = "ingredientDto",qualifiedByName = "mapToEntity")
    MeasuredIngredient mapToEntity(MeasuredIngredientDto dto);

    @Named("listMapToDtos")
    default List<MeasuredIngredientDto> listMapToDtos (List<MeasuredIngredient> entities){
        return entities.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Named("listMapToEntities")
    default List<MeasuredIngredient> listMapToEntities(List<MeasuredIngredientDto> dtos){
        return dtos.stream()
                .map(this::mapToEntity)
                .toList();
    }
}
