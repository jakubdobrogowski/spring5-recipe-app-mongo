package guru.springframework.spring5recipeapp.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"recipe"})
public class Notes {

    private String id;

    private String recipeNote;

    private Recipe recipe;

}

