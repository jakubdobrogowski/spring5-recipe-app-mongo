package guru.springframework.spring5recipeapp.model;


import guru.springframework.spring5recipeapp.infrastructure.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"recipe"})
public class Ingredient //extends BaseEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private UniteOfMesure uom;

    private String description; //ingridient Name
    private BigDecimal amount;

    @ManyToOne
    private Recipe recipe;

    public Ingredient(String description, BigDecimal amount, UniteOfMesure uom) {
        this.uom = uom;
        this.description = description;
        this.amount = amount;

    }

}
