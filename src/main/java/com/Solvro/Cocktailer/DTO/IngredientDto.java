package com.Solvro.Cocktailer.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class IngredientDto {
    private Long id;
    private String name;
    private String description;
    private boolean alcohol;
    private String imageUrl;

}
