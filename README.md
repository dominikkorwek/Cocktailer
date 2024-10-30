# Reddit Clone - Spring Boot Project

## Project Description

This project is built using Spring Boot
and its main goal is to help working
with cocktails by adding new ingredients,
selecting drinks with alcohol or deleting
and editing saved ones. Api uses mySql and
phpmyadmin from `docker-compose.yml`.
For more accurate documentation check
`http://localhost:8080/api/swagger-ui/index.html`.

## Features

- **Cocktails**: Create, get, edit and delete
  cocktails, get all cocktails.
- **Ingredients**: Create, get, edit and delete
  ingredients, get all ingredients.

## Technologies

- **Backend**: Spring Boot
- **Database**: MySQL and PHPmyAdmin in docker
- **Dependency Management**: Maven
- **Object Mapping**: Mapstruct
- **Lombok**: To reduce boilerplate code
- -**swagger-ui**: Simple documentation

### Models

- `Cocktails`: Represents cocktails.
- `ingredients`: Represents ingredients used
  to make cocktails.
- `MeasuredIngredient`: Represents ingredient
  with unit and quantity.

### DTOs

- `CocktailDto`
- `IngredientDto`
- `MeasuredIngredientDto`

### Services

- `CocktailService`: Manages cocktails.
- `IngredientService`: Manages ingredients.
- `MeasuredIngredientSerivce`: Manages measured
  ingredients.

### Mappers

- `CocktailMapper`: Maps between `Cocktail` entity
  and `CocktailDto`.
- `IngredientMapper`: Maps between `Ingredient`
  entity and `IngredientDto`.
- `MeasuredIngredientMapper`: Maps between
  `MeasuredIngredient` and `MeasuredIngredientDto`.

### Controllers

- **CocktailController**: Manages cocktails.
- `POST /cocktail/create`: Create new cocktail.
- `GET /cocktail/get/{id}`: Get cocktail by id.
- `GET /cocktail/get/all`:  Get all cocktails.
  Can be sorted by label, direction.
- `DELETE /cocktail/delete/{id}`: Delete cocktail
  by id.
- `PUT /cocktail/update/{id}` : Update cocktail
  by id.

- **IngredientController**: Manages ingredients.
- `POST /ingredient/create`: Create new ingredient.
- `GET /ingredient/get/{id}`: Get ingredient by id.
- `GET /ingredient/get/all`:  Get all ingredients.
  Can be sorted by label, direction and filtered
  by alcohol.
- `DELETE /ingredient/delete/{id}`: Delete ingredient
  by id.
- `PUT /ingredient/update/{id}` : Update ingredient
  by id.


## Getting Started

1. Clone the repository:
    ```bash
    git clone https://github.com/dominikkorwek/Cocktailer.git
    ```
   or by SSH
    ```bash
       git clone git@github.com:dominikkorwek/Cocktailer.git
    ```
2. Launch `docker-compose.yml`.
3. Run the application using Maven.
4. The application will be available at
   `http://localhost:8080`.


## Contributing

Contributions are welcome! If you'd like to add
new features, improve existing code, or fix bugs,
feel free to fork the repository and submit a pull
request. Please ensure your code is well-documented
and follows the project's coding standards.

## License

This project is licensed under the MIT License -
see the [LICENSE](LICENSE) file for details.

## Author

This project is maintained by **Dominik Korwek**.
If you have any questions, suggestions, or
feedback, feel free to reach out.