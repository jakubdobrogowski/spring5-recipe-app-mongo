package guru.springframework.spring5recipeapp.model;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"recipes"})
public class Category {

    private String id;

    private String categoryName;


    private Set<Recipe> recipes;

}
