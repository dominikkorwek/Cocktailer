package com.Solvro.Cocktailer.Service;

import com.Solvro.Cocktailer.DTO.IngredientDto;
import com.Solvro.Cocktailer.repository.IngredientRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IngredientServiceTest {
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private IngredientRepository ingredientRepository;

    private IngredientDto ingredientDtoInRepo;
    private IngredientDto ingredientDtoNotInRepo;


    @BeforeEach
    void setUp() {
        ingredientDtoInRepo = IngredientDto.builder()
                .alcohol(false)
                .name("orange")
                .description("yummy fruit")
                .imageUrl("orange image")
                .build();

        ingredientDtoNotInRepo = IngredientDto.builder()
                .id(5L)
                .alcohol(true)
                .name("vodka")
                .description("400% power")
                .imageUrl("vodka image")
                .build();
    }

    @AfterEach
    void tearDown() {
        ingredientRepository.deleteAll();
    }

    @Test
    void getById() {
        ingredientDtoInRepo = ingredientService.create(ingredientDtoInRepo);

        Long id = ingredientDtoInRepo.getId();
        IngredientDto possibleIngredient = ingredientService.getById(id);

        assertEquals(possibleIngredient, ingredientDtoInRepo);
    }

    @Test
    void getByIdInvalidNoSuchElement(){
        assertThrows(NoSuchElementException.class, () ->
        ingredientService.getById(ingredientDtoNotInRepo.getId())
        );
    }

    @Test
    void getByIdInvalidWrongId(){
        assertThrows(IllegalArgumentException.class, () ->
            ingredientService.getById(-1L)
        );
    }

    @Test
    void create() {
        ingredientDtoInRepo = ingredientService.create(ingredientDtoInRepo);

        assertNotNull(ingredientDtoInRepo.getId());
    }

    @Test
    void createInvalidDoubledEntity() {
        ingredientDtoInRepo = ingredientService.create(ingredientDtoInRepo);

        assertThrows( IllegalArgumentException.class, () ->
                ingredientDtoInRepo = ingredientService.create(ingredientDtoInRepo));
    }

    @Test
    void getAll() {
        ingredientDtoInRepo.setAlcohol(true);
        ingredientDtoInRepo = ingredientService.create(ingredientDtoInRepo);
        Sort sort = Sort.by("id").descending();

        List<IngredientDto> list = ingredientService.getAll(sort, null);

        assertNotNull(list);
        assertEquals( list.getFirst().getName(),ingredientDtoInRepo.getName());

        list = ingredientService.getAll(sort, true);

        assertNotNull(list);
    }

    @Test
    void getAllInvalidSortBy(){
        ingredientDtoInRepo = ingredientService.create(ingredientDtoInRepo);
        Sort sort = Sort.by("il").descending();

        assertThrows(PropertyReferenceException.class, () ->
                ingredientService.getAll(sort, null));
    }

    @Test
    void deleteById() {
        ingredientDtoInRepo = ingredientService.create(ingredientDtoInRepo);

        IngredientDto deleted = ingredientService.deleteById(ingredientDtoInRepo.getId());

        assertEquals( deleted.getName(), ingredientDtoInRepo.getName());

        assertThrows(NoSuchElementException.class, () ->
                ingredientService.getById(ingredientDtoInRepo.getId())
        );
    }

    @Test
    void updateById() {
        String newName = "monke";
        String oldName = ingredientDtoInRepo.getName();

        ingredientDtoInRepo = ingredientService.create(ingredientDtoInRepo);

        assertEquals(oldName,ingredientDtoInRepo.getName());

        ingredientDtoInRepo.setName(newName);

        assertNotEquals(oldName,ingredientDtoInRepo.getName());

        ingredientDtoInRepo = ingredientService.updateById(ingredientDtoInRepo.getId(), ingredientDtoInRepo);

        assertEquals( ingredientDtoInRepo.getName(),newName);
    }

    @Test
    void updateByIdInvalidNotTheSameId() {
        assertThrows(IllegalArgumentException.class, () ->
                ingredientService.updateById(10L, ingredientDtoInRepo)
                );
    }

    @Test
    void updateByIdInvalidWrongId() {
        assertThrows(IllegalArgumentException.class, () ->
                ingredientService.updateById(1L, ingredientDtoInRepo)
        );    }

    @Test
    void validateIngredientDto() {
        ingredientDtoInRepo = ingredientService.create(ingredientDtoInRepo);
        boolean isGood = ingredientService.validateIngredientDto(ingredientDtoInRepo);

        assertTrue(isGood);

        ingredientDtoNotInRepo.setId(ingredientDtoInRepo.getId());

        isGood = ingredientService.validateIngredientDto(ingredientDtoNotInRepo);

        assertFalse(isGood);
    }
}