package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.service.interfaces.IngredientService;
import guru.springframework.spring5recipeapp.service.interfaces.RecipeService;
import guru.springframework.spring5recipeapp.service.interfaces.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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

public class IngredientControllerTest {


    IngredientController controller;

    MockMvc mockMvc;

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

    }

    @Test
    public void testGetIngredientsById() throws Exception {

        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        //  recipeCommand.setId(2L);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //then
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testShowRecipeIngredient() throws Exception {

        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2L);
        ingredientCommand.setRecipeId(3L);

        //when
        when(ingredientService.findIngredientCommandByRecipeIdAndId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        //then
        mockMvc.perform(get("/recipe/3/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void testUpdateRecipeIngredient() throws Exception {

        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2L);
        ingredientCommand.setRecipeId(3L);

        //when
        when(ingredientService.findIngredientCommandByRecipeIdAndId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        //then
        mockMvc.perform(get("/recipe/3/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }


    @Test
    public void testNewIngredient() throws Exception {

        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        long id = 4434L;
        ingredientCommand.setId(id);

        //then
        mockMvc.perform(get("/recipe/4434/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void testDeletedIngredientById() throws Exception {

        //then
        mockMvc.perform(get("/recipe/2/ingredient/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));

        verify(ingredientService, times(1)).deleteIngredient(anyLong(), anyLong());


    }

    @Test
    public void testSaveOrUpdate() throws Exception {

        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(22L);
        ingredientCommand.setRecipeId(33L);

        //when
        when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);

        //then
        mockMvc.perform(post("/recipe/33/ingredient")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "")
        .param("description", "someString")
        ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/33/ingredient/22/show"));


    }


    @Test
    public void testHandleNumberFormatExceptionForIngredient() throws Exception {

        //then
        mockMvc.perform(get("/recipe/aaaaaa/ingredient/2/show"))
                .andExpect(view().name("400error"))
                .andExpect(status().isBadRequest());
    }
}