package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.exceptions.NotFoundException;
import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.service.implementations.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class RecipeControllerTest {

    RecipeController recipeController;

    MockMvc mockMvc;

    @Mock
    RecipeServiceImpl recipeServiceImpl;

    @Mock
    Model model;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeServiceImpl);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetById() throws Exception {

        //given
        Recipe recipe = new Recipe();
        recipe.setId("2");

        //when
        when(recipeServiceImpl.findById(anyLong())).thenReturn(recipe);

        //then
        mockMvc.perform(get("/recipe/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testGetByIdNotFound() throws Exception {

        //given
        Recipe recipe = new Recipe();
        recipe.setId("2");

        //when
        when(recipeServiceImpl.findById(anyLong())).thenThrow(NotFoundException.class);

        //then
        mockMvc.perform(get("/recipe/2/show"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testHandleNotFound() throws Exception {

        //given
        Recipe recipe = new Recipe();
        recipe.setId("244");

        //when
        when(recipeServiceImpl.findById(anyLong())).thenThrow(NotFoundException.class);

        //then
        mockMvc.perform(get("/recipe/244/show"))
                .andExpect(view().name("404error"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testHandleNumberFormatExceptionForRecipe() throws Exception {

        //then
        mockMvc.perform(get("/recipe/aaaaadddd/show"))
                .andExpect(view().name("400error"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testNewRecipe() throws Exception {

        //given
        RecipeCommand recipeCommand = new RecipeCommand();

        //then
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {

        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("2");

        //when
        when(recipeServiceImpl.saveRecipeCommand(any())).thenReturn(recipeCommand);

        //then
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some String")
                .param("directions", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void testSaveOrUpdateValidationFail() throws Exception {

        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("2");

        //when
        when(recipeServiceImpl.saveRecipeCommand(any())).thenReturn(recipeCommand);

        //then
        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void testRecipeUpdate() throws Exception {

        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("3");

        //when
        when(recipeServiceImpl.findCommandById(anyLong())).thenReturn(recipeCommand);

        //then
        mockMvc.perform(get("/recipe/3/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testDeletedAction() throws Exception {

        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeServiceImpl, times(1)).deleteRecipe(anyLong());

    }

}