package com.Solvro.Cocktailer.repository;

import com.Solvro.Cocktailer.model.Cocktail;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.lang.Nullable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    @Override
    @EntityGraph(attributePaths = {"measuredIngredients"})
    @NonNull
    List<Cocktail> findAll();

    @Override
    @EntityGraph(attributePaths = {"measuredIngredients"})
    @NonNull
    Optional<Cocktail> findById(@Nullable Long id);

    Optional<Cocktail> findByName(String name);

}
