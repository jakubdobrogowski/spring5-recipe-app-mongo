package guru.springframework.spring5recipeapp.service.interfaces;


import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getAllRecipe();

    Recipe findById(Long id);

    //save entity coming back from web tire
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(Long id);

    void deleteRecipe(Long id);
}
