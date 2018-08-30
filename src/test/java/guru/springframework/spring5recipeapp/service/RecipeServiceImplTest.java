package guru.springframework.spring5recipeapp.service;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.spring5recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.spring5recipeapp.exceptions.NotFoundException;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.service.implementations.RecipeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;


public class RecipeServiceImplTest {

    RecipeServiceImpl recipeServiceImpl;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        recipeServiceImpl = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void testGetAllRecipe() {

        //given
        Recipe recipe = new Recipe();
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);

        //when
        when(recipeServiceImpl.getAllRecipe()).thenReturn(recipes);

        //then
        Set<Recipe> allrecipe = recipeServiceImpl.getAllRecipe();
        Assert.assertEquals(allrecipe.size(), 1);
        Mockito.verify(recipeRepository, times(1)).findAll();

    }

    @Test
    public void testFindById() {

        //given
        Long id = 2L;
        Recipe recipe = new Recipe();
        recipe.setId(2L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //then
        assertNotNull("Null Recipe returned", recipeServiceImpl.findById(id));
        verify(recipeRepository, times(0)).findAll();
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void testFindByIdNotFound() {

        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeServiceImpl.findById(1L);

        //boooom
    }


    //saved nie testujemy


    @Test
    public void testFindCommandById() {

        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(3221L);

        Recipe recipe = new Recipe();
        recipe.setId(3221L);

        //when
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = recipeServiceImpl.findCommandById(1L);

        assertNotNull(commandById);
    }

    @Test
    public void testDeletedById() {

        //given
        Recipe recipe = new Recipe();
        long id = 332L;
        recipe.setId(id);

        //when
        recipeServiceImpl.deleteRecipe(id);

        //no 'when', since method has void return type

        //then
        verify(recipeRepository, times(1)).deleteById(anyLong());

    }
}