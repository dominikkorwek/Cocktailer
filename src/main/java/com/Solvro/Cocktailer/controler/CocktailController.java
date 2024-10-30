package com.Solvro.Cocktailer.controler;

import com.Solvro.Cocktailer.DTO.CocktailDto;
import com.Solvro.Cocktailer.Service.CocktailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cocktail")
@AllArgsConstructor
@Slf4j
public class CocktailController {

    private final CocktailService cocktailService;

    @PostMapping("/create")
    public ResponseEntity<CocktailDto> create(@RequestBody CocktailDto cocktailReq){
        CocktailDto cocktailRes = cocktailService.create(cocktailReq);

        return new ResponseEntity<>(cocktailRes,HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CocktailDto> get(@PathVariable Long id){
        CocktailDto cocktailRes = cocktailService.getById(id);

        return new ResponseEntity<>(cocktailRes,HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<CocktailDto>> getAll(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirect){

        Sort sort = sortDirect.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        List<CocktailDto> cocktailsRess = cocktailService.getAll(sort);

        return new ResponseEntity<>(cocktailsRess,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CocktailDto> delete(@PathVariable Long id){
        CocktailDto cocktailRes = cocktailService.deleteById(id);

        return new ResponseEntity<>(cocktailRes,HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CocktailDto> update(@PathVariable Long id,
                                              @RequestBody CocktailDto cocktailReq){
        CocktailDto cocktailRes = cocktailService.updateById(id,cocktailReq);

        return new ResponseEntity<>(cocktailRes,HttpStatus.OK);
    }
}
