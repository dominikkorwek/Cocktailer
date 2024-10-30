package com.Solvro.Cocktailer.Service;

import com.Solvro.Cocktailer.DTO.CocktailDto;
import com.Solvro.Cocktailer.DTO.MeasuredIngredientDto;
import com.Solvro.Cocktailer.mapper.MeasuredIngredientMapper;
import com.Solvro.Cocktailer.mapper.CocktailMapper;
import com.Solvro.Cocktailer.model.Cocktail;
import com.Solvro.Cocktailer.model.CocktailCategoryEnum;
import com.Solvro.Cocktailer.repository.CocktailRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CocktailService {

    @PersistenceContext
    private EntityManager entityManager;

    private final CocktailMapper cocktailMapper;
    private final CocktailIngredientService cocktailIngredientService;
    private final CocktailRepository cocktailRepository;
    private final MeasuredIngredientMapper measuredIngredientMapper;

    @Transactional
    public CocktailDto create(CocktailDto cocktailReq) {
        String name = cocktailReq.getName();

        if(cocktailRepository.findByName(name).isPresent())
            throw new IllegalArgumentException("cocktail with name: " + name + " exists");

        List<MeasuredIngredientDto> measuredIngredientDtos = cocktailReq.getIngredients();

        measuredIngredientDtos = cocktailIngredientService.validateListDto(measuredIngredientDtos);
        cocktailReq.setIngredients(measuredIngredientDtos);

        Cocktail cocktail = cocktailMapper.mapToEntity(cocktailReq);


        cocktail = cocktailRepository.save(cocktail);
        entityManager.refresh(cocktail);

        return cocktailMapper.mapToDto(cocktail);
    }

    public CocktailDto getById(Long id) {
        if (id < 0)
            throw new IllegalArgumentException("id is lower than 0");

        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("cocktail with id: " + id + " does not Exist"));

        return cocktailMapper.mapToDto(cocktail);
    }

    public List<CocktailDto> getAll(Sort sort) {
        return cocktailRepository.findAll(sort).stream()
                .map(cocktailMapper::mapToDto)
                .toList();
    }

    @Transactional
    public CocktailDto deleteById(Long id){
        CocktailDto dto = getById(id);
        cocktailRepository.deleteById(id);

        return dto;
    }

    @Transactional
    public CocktailDto updateById(Long id, CocktailDto cocktailReq) {
        if (!id.equals(cocktailReq.getId()))
            throw new IllegalArgumentException("cocktail's id and id are different");

        Cocktail cocktail = cocktailRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("cocktail with id: " + id + " does not Exist"));

        cocktail.setName(cocktailReq.getName());
        cocktail.setCategory(CocktailCategoryEnum.valueOf(cocktailReq.getCategory()));
        cocktail.setInstruction(cocktailReq.getInstruction());

        cocktailIngredientService.validateListDto(cocktailReq.getIngredients());

        cocktail.setMeasuredIngredients(measuredIngredientMapper
                .listMapToEntities(cocktailReq.getIngredients()));

        cocktail = cocktailRepository.save(cocktail);

        return cocktailMapper.mapToDto(cocktail);
    }

}
