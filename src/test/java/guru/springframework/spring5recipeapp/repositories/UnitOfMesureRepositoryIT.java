package guru.springframework.spring5recipeapp.repositories;

import guru.springframework.spring5recipeapp.model.UniteOfMesure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMesureRepositoryIT {

    @Autowired
    UnitOfMesureRepository unitOfMesureRepository;

    @Before
    public void setUp()  {

    }

    @Test
    public void testFindByUom() {

        Optional<UniteOfMesure> teaspoon = unitOfMesureRepository.findByUom("Teaspoon");
        assertEquals("Teaspoon", teaspoon.get().getUom());
    }


    @Test
    public void findByUomCup(){

        Optional<UniteOfMesure> cup = unitOfMesureRepository.findByUom("Cup");
        assertEquals("Cup", cup.get().getUom());
    }

}