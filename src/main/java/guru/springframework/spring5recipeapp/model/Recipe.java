package guru.springframework.spring5recipeapp.model;

import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
public class Recipe {


    private String id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;


    private String directions;

    private Difficulty difficulty;


    private Set<Category> categories = new HashSet<>();


    private Set<Ingredient> ingredients = new HashSet<>();


    private Byte[] image;


    private Notes notes;


    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
            notes.setRecipe(this);
        }
    }

    public void addIngredient(Ingredient ingredient) {

        ingredient.setRecipe(this);
        ingredients.add(ingredient);
    }
}
