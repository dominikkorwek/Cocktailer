package com.Solvro.Cocktailer.Service;

import com.Solvro.Cocktailer.DTO.CocktailDto;
import com.Solvro.Cocktailer.DTO.MeasuredIngredientDto;
import com.Solvro.Cocktailer.DTO.IngredientDto;
import com.Solvro.Cocktailer.model.CocktailCategoryEnum;
import com.Solvro.Cocktailer.model.UnitEnum;
import com.Solvro.Cocktailer.repository.CocktailRepository;
import com.Solvro.Cocktailer.repository.IngredientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CocktailServiceTest {

    @Autowired
    private CocktailService cocktailService;
    @Autowired
    private CocktailRepository cocktailRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private IngredientService ingredientService;

    private CocktailDto cocktailInRepo;
    private CocktailDto getCocktailNotInRepo;

    private IngredientDto orange;


    @BeforeEach
    void setUp() {
        orange = IngredientDto.builder()
                .alcohol(false)
                .name("orange")
                .description("yummy fruit")
                .imageUrl("orange image")
                .build();

        IngredientDto water = IngredientDto.builder()
                .id(5L)
                .alcohol(false)
                .name("water")
                .description("just water")
                .imageUrl("water image")
                .build();

        orange = ingredientService.create(orange);
        water = ingredientService.create(water);

        List<MeasuredIngredientDto> list = new ArrayList<>();
        list.add(MeasuredIngredientDto.builder()
                        .ingredientDto(orange)
                        .unit(UnitEnum.gram.name())
                        .quantity(20D)
                        .build());

        list.add(MeasuredIngredientDto.builder()
                .ingredientDto(water)
                .unit(UnitEnum.spoon.name())
                .quantity(200D)
                .build());

        cocktailInRepo = CocktailDto.builder()
                .name("Orange juice")
                .category(CocktailCategoryEnum.JUICE.name())
                .ingredients(list)
                .instruction("add orange to water")
                .build();

        getCocktailNotInRepo = CocktailDto.builder()
                .name("water with oranges")
                .category(CocktailCategoryEnum.COCKTAIL.name())
                .ingredients(list)
                .instruction("add water to oranges")
                .build();
    }

    @AfterEach
    void tearDown() {
        cocktailRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    @Test
    void create() {
        CocktailDto created = cocktailService.create(cocktailInRepo);

        assertEquals(created.getName(),cocktailInRepo.getName());
    }

    @Test
    void createInvalid(){
        orange.setName("not orange");

        cocktailInRepo.getIngredients().add(
                MeasuredIngredientDto.builder()
                        .unit("miau")
                        .quantity(200000D)
                        .ingredientDto(orange)
                        .build()
        );

        assertThrows( IllegalArgumentException.class,() ->
                cocktailService.create(cocktailInRepo)
                );
    }

    @Test
    void createInvalidDoubledEntity(){
        cocktailService.create(cocktailInRepo);

        assertThrows(IllegalArgumentException.class, ()->
                cocktailService.create(cocktailInRepo));
    }

    @Test
    void getById() {
        cocktailInRepo = cocktailService.create(cocktailInRepo);

        Long id = cocktailInRepo.getId();

        CocktailDto possibleCocktail = cocktailService.getById(id);

        assertEquals(cocktailInRepo,possibleCocktail);
    }

    @Test
    void getByIdInvalidNoSuchElement(){
        assertThrows(NoSuchElementException.class, () ->
                cocktailService.getById(10L)
        );
    }

    @Test
    void getByIdInvalidWrongId(){
        assertThrows(IllegalArgumentException.class, () ->
                cocktailService.getById(-1L)
        );
    }

    @Test
    void getAll() {
        cocktailInRepo = cocktailService.create(cocktailInRepo);
        Sort sort = Sort.by("id").descending();


        List<CocktailDto> list = cocktailService.getAll(sort);

        assertNotNull(list);
        assertEquals(cocktailInRepo.getId(), list.getFirst().getId());
    }

    @Test
    void getAllInvalid(){
        cocktailInRepo = cocktailService.create(cocktailInRepo);
        Sort sort = Sort.by("il").descending();

        cocktailService.getAll(sort);
    }

    @Test
    void deleteById() {
        cocktailInRepo = cocktailService.create(cocktailInRepo);

        CocktailDto deleted = cocktailService.deleteById(cocktailInRepo.getId());

        assertEquals(cocktailInRepo.getName(), deleted.getName());

        assertThrows(NoSuchElementException.class, () ->
                cocktailService.getById(cocktailInRepo.getId())
        );
    }

    @Test
    void updateById() {
    }

    @Test
    void updateByIdInvalidId() {
        cocktailInRepo = cocktailService.create(cocktailInRepo);

        cocktailInRepo.setName("not oranges");


        assertThrows(IllegalArgumentException.class,() ->
                cocktailService.updateById(5L,cocktailInRepo));
    }

    @Test
    void updateByIdInvalidEntity() {
        cocktailInRepo = cocktailService.create(cocktailInRepo);

        cocktailInRepo.setName("not oranges");


        assertThrows(IllegalArgumentException.class,() ->
                cocktailService.updateById(cocktailInRepo.getId(),getCocktailNotInRepo));
    }
}