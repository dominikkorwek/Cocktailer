package com.Solvro.Cocktailer.Service;

import com.Solvro.Cocktailer.DTO.IngredientDto;
import com.Solvro.Cocktailer.mapper.IngredientMapper;
import com.Solvro.Cocktailer.model.Ingredient;
import com.Solvro.Cocktailer.repository.IngredientRepository;
import com.Solvro.Cocktailer.specification.IngredientSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public IngredientDto getById(Long id){
        if (id < 0)
            throw new IllegalArgumentException("id is lower than 0");

        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Ingredient with id: " + id + "does not exist")
                );

        return ingredientMapper.mapToDto(ingredient);
    }

    public IngredientDto create(IngredientDto ingredientReq) {
        String name = ingredientReq.getName();

        if(ingredientRepository.findByName(name).isPresent())
            throw new IllegalArgumentException("ingredient with name: " + name + " exists");

        Ingredient ingredient = ingredientMapper.mapToEntity(ingredientReq);


        ingredient = ingredientRepository.save(ingredient);

        return ingredientMapper.mapToDto(ingredient);
    }

    public List<IngredientDto> getAll(Sort sort, Boolean filtrByAlcohol) {
        Specification<Ingredient> spec = Specification.where(null);

        if (filtrByAlcohol != null)
            spec.and(IngredientSpecification.isAlcohol(filtrByAlcohol));

        return ingredientRepository.findAll(spec,sort).stream()
                .map(ingredientMapper::mapToDto)
                .toList();
    }

    public IngredientDto deleteById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(
                () -> new NoSuchElementException("ingredient with id: " + id + " does not exist"));

        ingredientRepository.deleteById(id);

        return ingredientMapper.mapToDto(ingredient);
    }

    public IngredientDto updateById(Long id, IngredientDto ingredientReq) {
        if (!id.equals(ingredientReq.getId()))
            throw new IllegalArgumentException("ingredient's id and id are different");

        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("ingredient with id: " + id + " does not Exist"));

        ingredient.setAlcohol(ingredientReq.isAlcohol());
        ingredient.setName(ingredientReq.getName());
        ingredient.setDescription(ingredientReq.getDescription());
        ingredient.setImageUrl(ingredientReq.getImageUrl());

        ingredient = ingredientRepository.save(ingredient);

        return ingredientMapper.mapToDto(ingredient);
    }

    public boolean validateIngredientDto(IngredientDto possibleIngredientDto){
        IngredientDto ingredient = getById(possibleIngredientDto.getId());

        return ingredient.getName().equals(possibleIngredientDto.getName());
    }
}