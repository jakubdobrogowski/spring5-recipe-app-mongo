package guru.springframework.spring5recipeapp.model;

import guru.springframework.spring5recipeapp.infrastructure.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class UniteOfMesure //extends BaseEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uom;

}