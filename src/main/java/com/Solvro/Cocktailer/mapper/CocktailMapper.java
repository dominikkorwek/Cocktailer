package com.Solvro.Cocktailer.mapper;

import com.Solvro.Cocktailer.DTO.CocktailDto;
import com.Solvro.Cocktailer.model.Cocktail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MeasuredIngredientMapper.class)
public interface CocktailMapper {

    @Mapping(target = "ingredients",source = "measuredIngredients",qualifiedByName = "listMapToDtos")
    CocktailDto mapToDto(Cocktail cocktail);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measuredIngredients",source = "ingredients",qualifiedByName = "listMapToEntities")
    Cocktail mapToEntity(CocktailDto dto);

}
