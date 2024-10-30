package com.Solvro.Cocktailer.Service;

import com.Solvro.Cocktailer.DTO.MeasuredIngredientDto;
import com.Solvro.Cocktailer.DTO.IngredientDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CocktailIngredientService {

    private final IngredientService ingredientService;

    public List<MeasuredIngredientDto> validateListDto(List<MeasuredIngredientDto> dtoList) {
        dtoList.forEach(this::validateCocktailIngredientDto);

        return dtoList;
    }

    private void validateCocktailIngredientDto(MeasuredIngredientDto dto){
        IngredientDto ingredientDto = dto.getIngredientDto();

        if (!ingredientService.validateIngredientDto(ingredientDto))
            throw new IllegalArgumentException("ingredient: "+ dto +" is not valid");

    }
}