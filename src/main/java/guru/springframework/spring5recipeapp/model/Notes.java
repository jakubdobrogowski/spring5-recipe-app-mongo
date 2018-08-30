package guru.springframework.spring5recipeapp.model;

import guru.springframework.spring5recipeapp.infrastructure.BaseEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"recipe"})
public class Notes //extends BaseEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String recipeNote;

    @OneToOne
    private Recipe recipe;

}

