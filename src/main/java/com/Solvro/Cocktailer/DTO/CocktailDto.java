package com.Solvro.Cocktailer.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CocktailDto {
    private Long id;
    private String name;
    private String category;
    private String instruction;
    private List<MeasuredIngredientDto> ingredients;
}
