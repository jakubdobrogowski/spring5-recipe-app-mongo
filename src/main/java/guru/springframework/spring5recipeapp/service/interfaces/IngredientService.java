package guru.springframework.spring5recipeapp.service.interfaces;


import guru.springframework.spring5recipeapp.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findIngredientCommandByRecipeIdAndId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteIngredient(Long aLong, Long aLong1);
}
