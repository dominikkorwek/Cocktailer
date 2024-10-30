package com.Solvro.Cocktailer.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasuredIngredientDto {
    private Long id;
    private IngredientDto ingredientDto;
    private Double quantity;
    private String unit;
}
