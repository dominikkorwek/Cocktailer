package com.Solvro.Cocktailer.controler;

import com.Solvro.Cocktailer.DTO.IngredientDto;
import com.Solvro.Cocktailer.Service.IngredientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@AllArgsConstructor
@Slf4j
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping("/create")
    public ResponseEntity<IngredientDto> create(@RequestBody IngredientDto ingredientReq){
        IngredientDto ingredientRes = ingredientService.create(ingredientReq);

        return new ResponseEntity<>(ingredientRes, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<IngredientDto> get(@PathVariable Long id){
        IngredientDto ingredientRes = ingredientService.getById(id);

        return new ResponseEntity<>(ingredientRes, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<IngredientDto>> getAll(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirect,
            @RequestParam(required = false) Boolean filtrByAlcohol){

        Sort sort = sortDirect.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        List<IngredientDto> ingredientRess = ingredientService.getAll(sort,filtrByAlcohol);

        return new ResponseEntity<>(ingredientRess, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<IngredientDto> delete(@PathVariable Long id){
        IngredientDto ingredientRes = ingredientService.deleteById(id);

        return new ResponseEntity<>(ingredientRes, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<IngredientDto> update(@PathVariable Long id,@RequestBody IngredientDto ingredientReq){
        IngredientDto ingredientRes = ingredientService.updateById(id,ingredientReq);

        return new ResponseEntity<>(ingredientRes, HttpStatus.OK);
    }
}
