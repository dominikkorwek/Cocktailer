package com.Solvro.Cocktailer.repository;

import com.Solvro.Cocktailer.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>,
        JpaSpecificationExecutor<Ingredient> {

    Optional<Ingredient> findByName(String name);
    List<Ingredient> findAllByAlcohol(boolean isAlcohol);
}
