package guru.springframework.spring5recipeapp.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"recipe"})
public class Ingredient {


    private String id;

    private UniteOfMesure uom;

    private String description; //ingridient Name
    private BigDecimal amount;


    private Recipe recipe;

    public Ingredient(String description, BigDecimal amount, UniteOfMesure uom) {
        this.uom = uom;
        this.description = description;
        this.amount = amount;

    }

}
