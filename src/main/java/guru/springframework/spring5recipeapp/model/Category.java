package guru.springframework.spring5recipeapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"recipes"})
public class Category //extends BaseEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
