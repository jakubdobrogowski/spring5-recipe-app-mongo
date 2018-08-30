package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.service.interfaces.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("I'm in controller index");
        model.addAttribute("recipes", recipeService.getAllRecipe());

        return "index";
    }

}
