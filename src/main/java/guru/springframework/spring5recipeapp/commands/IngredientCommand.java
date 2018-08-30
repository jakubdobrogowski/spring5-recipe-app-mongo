package guru.springframework.spring5recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class IngredientCommand {

    private String id;
    private String recipeId;
    private UnitOfMesureCommand uom;
    private String description;
    private BigDecimal amount;
}
