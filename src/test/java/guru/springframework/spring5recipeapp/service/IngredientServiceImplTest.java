package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.model.Ingredient;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMesureRepository;
import guru.springframework.spring5recipeapp.service.implementations.IngredientServiceImpl;
import guru.springframework.spring5recipeapp.service.interfaces.IngredientService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class IngredientServiceImplTest {

    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;


    @Mock
    UnitOfMesureRepository unitOfMesureRepository;
    @Mock
    RecipeRepository recipeRepository;

    IngredientService ingredientService;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeRepository, unitOfMesureRepository);
    }


    @Test
    public void findByRecipeIdAndReceipeIdHappyPath() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(3L);
        recipe.addIngredient(ingredient1);

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        //when
        IngredientCommand ingredientCommand = ingredientService.findIngredientCommandByRecipeIdAndId(1L, 3L);

        //then
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }



    @Test
    public void testSaveIngredientCommand() {

        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeId(4666L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());


        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId(3L);


        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);


        //when
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //than
        assertEquals(Long.valueOf(3L), savedIngredientCommand.getId());


    }


    @Test
    public void testDeleteIngredient() {

        //given
        Long idIngredientToDelited = 2L;
        Long idRecipe = 2L;
        Recipe recipe = new Recipe();
        recipe.setId(idRecipe);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(idIngredientToDelited);
        ingredient.setRecipe(recipe);
        recipe.addIngredient(ingredient);

        when(recipeRepository.findById(idIngredientToDelited)).thenReturn(Optional.of(recipe));

        //when
        ingredientService.deleteIngredient(idRecipe, idIngredientToDelited);

        //than
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));


    }
}