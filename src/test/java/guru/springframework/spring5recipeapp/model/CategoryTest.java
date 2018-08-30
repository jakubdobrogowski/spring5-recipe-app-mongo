package guru.springframework.spring5recipeapp.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CategoryTest {

    Category category;

    @Before
    public void setUp() {

        category = new Category();
    }

    @Test
    public void getId() {

        Long idValue = 4L;
        category.setId(idValue);
        assertEquals(idValue, category.getId());
    }

    @Test
    public void findByDescription() {

        String categoryName = "American";
        category.setCategoryName(categoryName);
        assertEquals(categoryName, category.getCategoryName());
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        recipe.setDifficulty(Difficulty.EASY);
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);
        category.setRecipes(recipes);
        assertEquals(recipes, category.getRecipes());

    }
}