package guru.springframework.spring5recipeapp.bootstrap;

import guru.springframework.spring5recipeapp.model.*;
import guru.springframework.spring5recipeapp.repositories.CategoryRepository;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMesureRepository;
import guru.springframework.spring5recipeapp.model.UniteOfMesure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@Profile("default")
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMesureRepository unitOfMesureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMesureRepository unitOfMesureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMesureRepository = unitOfMesureRepository;

    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
        log.debug("Loading Bootstrap class");

    }

    public List<Recipe> getRecipes() {

        //Create return List of Recipe, to be more specific only to Recipes
        List<Recipe> recipes = new ArrayList<>(2);


        //get Unite Of Mesure
        Optional<UniteOfMesure> teaspoonOptional = unitOfMesureRepository.findByUom("Teaspoon");
        if (!teaspoonOptional.isPresent()) {

            throw new RuntimeException("Expected Uom Not Found");
        }

        Optional<UniteOfMesure> tablespoonOptional = unitOfMesureRepository.findByUom("Tablespoon");
        if (!tablespoonOptional.isPresent()) {

            throw new RuntimeException("Expected Uom Not Found");
        }

        Optional<UniteOfMesure> cupOptional = unitOfMesureRepository.findByUom("Cup");
        if (!cupOptional.isPresent()) {

            throw new RuntimeException("Expected Uom Not Found");
        }

        Optional<UniteOfMesure> ounceOptional = unitOfMesureRepository.findByUom("Ounce");
        if (!ounceOptional.isPresent()) {

            throw new RuntimeException("Expected Uom Not Found");
        }
        Optional<UniteOfMesure> dashOptional = unitOfMesureRepository.findByUom("Dash");
        if (!dashOptional.isPresent()) {

            throw new RuntimeException("Expected Uom Not Found");
        }
        Optional<UniteOfMesure> pintOptional = unitOfMesureRepository.findByUom("Pint");
        if (!pintOptional.isPresent()) {

            throw new RuntimeException("Expected Uom Not Found");
        }
        Optional<UniteOfMesure> eachOptional = unitOfMesureRepository.findByUom("Each");
        if (!eachOptional.isPresent())

            throw new RuntimeException("Expected Uom Not Found");

        //get Optionals
        UniteOfMesure teaspoon = teaspoonOptional.get();
        UniteOfMesure tablespoon = tablespoonOptional.get();
        UniteOfMesure cup = cupOptional.get();
        UniteOfMesure ounce = ounceOptional.get();
        UniteOfMesure dash = dashOptional.get();
        UniteOfMesure pint = pintOptional.get();
        UniteOfMesure each = eachOptional.get();

        //get Category
        Optional<Category> americanOptional = categoryRepository.findByCategoryName("American");
        if (!americanOptional.isPresent()) {

            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> mexicanOptional = categoryRepository.findByCategoryName("Mexican");
        if (!mexicanOptional.isPresent()) {

            throw new RuntimeException("Expected Category Not Found");
        }

        //get Optionals
        Category american = americanOptional.get();
        Category mexican = mexicanOptional.get();


        //How to Make Perfect Guacamole
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("How to Make Perfect Guacamole");
        guacRecipe.setPrepTime(13);
        guacRecipe.setCookTime(10);
        guacRecipe.setServings(4);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");

        Notes guacNote = new Notes();
        guacNote.setRecipeNote("The trick to making perfect guacamole is using good, ripe avocados.\n" +
                "\n" +
                "Check for ripeness by gently pressing the outside of the avocado.\n" +
                "\n" +
                "If there is no give, the avocado is not ripe yet and will not taste good. \n" +
                "\n" +
                "If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be past ripe and not good. \n" +
                "\n" +
                "In this case, taste test first before using");

        guacRecipe.setNotes(guacNote);
        //guacNote.setRecipe(guacRecipe);


//        guacRecipe.getIngredients().add(new Ingredient("ripe avocados", new BigDecimal(2), each, guacRecipe));
//        guacRecipe.getIngredients().add(new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoon, guacRecipe));
//        guacRecipe.getIngredients().add(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tablespoon, guacRecipe));
//        guacRecipe.getIngredients().add(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tablespoon, guacRecipe));
//        guacRecipe.getIngredients().add(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), each, guacRecipe));
//        guacRecipe.getIngredients().add(new Ingredient("Cilantro", new BigDecimal(2), tablespoon, guacRecipe));
//        guacRecipe.getIngredients().add(new Ingredient("freshly grated black pepper", new BigDecimal(2), dash, guacRecipe));
//        guacRecipe.getIngredients().add(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), each, guacRecipe));

        guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), each));
        guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoon));
        guacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tablespoon));
        guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tablespoon));
        guacRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), each));
        guacRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tablespoon));
        guacRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dash));
        guacRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), each));


        guacRecipe.getCategories().add(american);
        guacRecipe.getCategories().add(mexican);

        //add to return list
        recipes.add(guacRecipe);

        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);

        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNote("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
        tacosRecipe.setNotes(tacoNotes);
        //tacosRecipe.setNotes(tacoNotes);


        tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tablespoon));
        tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), each));
        tacosRecipe.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tablespoon));
        tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tablespoon));
        tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tablespoon));
        tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tablespoon));
        tacosRecipe.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), each));
        tacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cup));
        tacosRecipe.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), each));
        tacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), each));
        tacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pint));
        tacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), each));
        tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), each));
        tacosRecipe.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cup));
        tacosRecipe.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), each));

        tacosRecipe.getCategories().add(american);
        tacosRecipe.getCategories().add(mexican);

        recipes.add(tacosRecipe);
        return recipes;
    }


}

