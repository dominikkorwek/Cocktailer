package com.Solvro.Cocktailer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CocktailCategoryEnum category;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String instruction;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MeasuredIngredient> measuredIngredients;
}