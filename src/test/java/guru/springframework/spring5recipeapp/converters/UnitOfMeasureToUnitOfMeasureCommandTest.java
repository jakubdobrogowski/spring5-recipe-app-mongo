package guru.springframework.spring5recipeapp.converters;


import guru.springframework.spring5recipeapp.commands.UnitOfMesureCommand;
import guru.springframework.spring5recipeapp.model.UniteOfMesure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {


    public static final String DESCRIPTION = "description";
    public static final String STRING_VALUE = "1";

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObjectConvert() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertNotNull(converter.convert(new UniteOfMesure()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UniteOfMesure uom = new UniteOfMesure();
        uom.setId(STRING_VALUE);
        uom.setUom(DESCRIPTION);
        //when
        UnitOfMesureCommand uomc = converter.convert(uom);

        //then
        assertEquals(STRING_VALUE, uomc.getId());
        assertEquals(DESCRIPTION, uomc.getUom());
    }

}