package com.Solvro.Cocktailer.specification;

import com.Solvro.Cocktailer.model.Ingredient;
import org.springframework.data.jpa.domain.Specification;

public class IngredientSpecification {
    public static Specification<Ingredient> isAlcohol(Boolean alcohol){
        return (root, query, cb) -> cb.equal(root.get("alcohol"),alcohol);
    }
}
