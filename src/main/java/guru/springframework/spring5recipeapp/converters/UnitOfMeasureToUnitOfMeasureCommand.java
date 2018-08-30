package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.UnitOfMesureCommand;
import guru.springframework.spring5recipeapp.model.UniteOfMesure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UniteOfMesure, UnitOfMesureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMesureCommand convert(UniteOfMesure source) {
        if (source == null) {

            return null;
        }
        UnitOfMesureCommand unitOfMesureCommand = new UnitOfMesureCommand();
        unitOfMesureCommand.setId(source.getId());
        unitOfMesureCommand.setUom(source.getUom());
        return unitOfMesureCommand;
    }
}
