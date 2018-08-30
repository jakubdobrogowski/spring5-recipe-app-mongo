package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.commands.UnitOfMesureCommand;
import guru.springframework.spring5recipeapp.service.interfaces.IngredientService;
import guru.springframework.spring5recipeapp.service.interfaces.RecipeService;
import guru.springframework.spring5recipeapp.service.interfaces.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String getIngredientsById(Model model, @PathVariable String id) {

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id, Model model) {

        model.addAttribute("ingredient", ingredientService.findIngredientCommandByRecipeIdAndId(Long.valueOf(recipeId), Long.valueOf(id)));

        return "recipe/ingredient/show";
    }


    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id, Model model) {

        model.addAttribute("ingredient", ingredientService.findIngredientCommandByRecipeIdAndId(Long.valueOf(recipeId), Long.valueOf(id)));

        model.addAttribute("uomList", unitOfMeasureService.getListAllUom()); //bo chcemy sobie wybierac

        return "recipe/ingredient/ingredientform";
    }


    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {

        //make sure we have good id value
        RecipeCommand commandById = recipeService.findCommandById(Long.valueOf(recipeId)); //todo raise exception if null

        model.addAttribute("ingredient", getBrandNewIngredientCommand(recipeId));

        model.addAttribute("uomList", unitOfMeasureService.getListAllUom());

        return "recipe/ingredient/ingredientform";
    }

    private IngredientCommand getBrandNewIngredientCommand(@PathVariable String recipeId) {

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        //init uom
        ingredientCommand.setUom(new UnitOfMesureCommand());
        return ingredientCommand;
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id) {

        ingredientService.deleteIngredient(Long.valueOf(recipeId), Long.valueOf(id));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {

        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";
    }
}