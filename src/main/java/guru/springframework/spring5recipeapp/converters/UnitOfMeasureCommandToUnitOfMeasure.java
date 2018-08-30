package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.UnitOfMesureCommand;
import guru.springframework.spring5recipeapp.model.UniteOfMesure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMesureCommand, UniteOfMesure> {


    @Synchronized
    @Nullable
    @Override
    public UniteOfMesure convert(UnitOfMesureCommand source) {
        if (source == null) {

            return null;
        }
       final UniteOfMesure uniteOfMesure = new UniteOfMesure();
        uniteOfMesure.setId(source.getId());
        uniteOfMesure.setUom(source.getUom());
        return uniteOfMesure;
    }
}
