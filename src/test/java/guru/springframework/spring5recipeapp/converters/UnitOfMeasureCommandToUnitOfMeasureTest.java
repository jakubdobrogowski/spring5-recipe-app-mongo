package guru.springframework.spring5recipeapp.converters;

import guru.springframework.spring5recipeapp.commands.UnitOfMesureCommand;
import guru.springframework.spring5recipeapp.model.UniteOfMesure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final String DESCRIPTION = "description";
    public static final String STRING_VALUE = "1";

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();

    }

    @Test
    public void testNullParamter() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new UnitOfMesureCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitOfMesureCommand command = new UnitOfMesureCommand();
        command.setId(STRING_VALUE);
        command.setUom(DESCRIPTION);

        //when
        UniteOfMesure uom = converter.convert(command);

        //then
        assertNotNull(uom);
        assertEquals(STRING_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getUom());

    }
}