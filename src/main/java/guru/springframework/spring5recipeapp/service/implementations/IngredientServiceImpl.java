package guru.springframework.spring5recipeapp.service.implementations;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.model.Ingredient;
import guru.springframework.spring5recipeapp.model.Recipe;

import guru.springframework.spring5recipeapp.model.UniteOfMesure;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMesureRepository;
import guru.springframework.spring5recipeapp.service.interfaces.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private RecipeRepository recipeRepository;
    private UnitOfMesureRepository unitOfMesureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository, UnitOfMesureRepository unitOfMesureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;

        this.recipeRepository = recipeRepository;
        this.unitOfMesureRepository = unitOfMesureRepository;
    }

    @Override
    public IngredientCommand findIngredientCommandByRecipeIdAndId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {

            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent()) {

            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();


    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if (!recipeOptional.isPresent()) {

            //todo toss error if not found!
            log.error("Recipe Not Found For Id: " + ingredientCommand.getId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = findIngredient(ingredientCommand, recipe);  //poszukaj czy mamy taki składnik już

            if (ingredientOptional.isPresent()) {
                updateIngredient(ingredientCommand, ingredientOptional); //jak mamy to uaktualnij
            } else {
                addNewIngredient(ingredientCommand, recipe); //jak nie to stówrz taki
            }

            Recipe savedRecipe = recipeRepository.save(recipe); //zapisz

            Optional<Ingredient> savedIngrdientOptional = checkIfThereIsDuplicateForExisting(ingredientCommand, savedRecipe);  //sprawdz czy się zapiało DLA EDYTOWANEGO

            if (!savedIngrdientOptional.isPresent()) {

                savedIngrdientOptional = checkIdThereIsDuplicateForNewIngredient(ingredientCommand, savedRecipe); //sprawdz czy się zapiało DLA NOWEGO - BO NOWY NIE MA ID
            }

            return ingredientToIngredientCommand.convert(savedIngrdientOptional.get());
        }

    }

    @Override
    public void deleteIngredient(Long recipeId, Long id) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {

            log.debug("Recipe Not Found " + recipeId);
            throw new RuntimeException("Recipe Not Found");
        }
        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst();
        if (!ingredientOptional.isPresent()){

            log.debug("Ingredient Not Found " + id);
            throw new RuntimeException("Ingredient Not Found");
        }
        Ingredient ingredientToDelete = ingredientOptional.get();
        ingredientToDelete.setRecipe(null); //wazne
        recipe.getIngredients().remove(ingredientToDelete);
        recipeRepository.save(recipe);
    }



    private Optional<Ingredient> checkIfThereIsDuplicateForExisting(IngredientCommand ingredientCommand, Recipe savedRecipe) {
        return savedRecipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();
    }

    private Optional<Ingredient> checkIdThereIsDuplicateForNewIngredient(IngredientCommand ingredientCommand, Recipe savedRecipe) {
        Optional<Ingredient> savedIngrdientOptional = null;

        Set<Ingredient> ingredients = savedRecipe.getIngredients();
        if(ingredients.isEmpty()) {
            savedIngrdientOptional = ingredients.stream()
                    .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                    .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                    .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUom().getId()))
                    .findFirst();
        }
        return savedIngrdientOptional;
    }

    private void addNewIngredient(IngredientCommand ingredientCommand, Recipe recipe) {
        recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
    }

    private void updateIngredient(IngredientCommand ingredientCommand, Optional<Ingredient> ingredientOptional) {
        Ingredient ingredientFound = ingredientOptional.get();
        ingredientFound.setDescription(ingredientCommand.getDescription());
        ingredientFound.setAmount(ingredientCommand.getAmount());
        ingredientFound.setUom(getUom(ingredientCommand));
    }

    private Optional<Ingredient> findIngredient(IngredientCommand ingredientCommand, Recipe recipe) {
        return recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();
    }

    private UniteOfMesure getUom(IngredientCommand ingredientCommand) {
        return unitOfMesureRepository
                .findById(ingredientCommand.getId())
                .orElseThrow(() -> new RuntimeException("Unit Of Measure Not Found")); //todo adress this
    }
}
