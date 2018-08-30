package guru.springframework.spring5recipeapp.repositories;

import guru.springframework.spring5recipeapp.model.UniteOfMesure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMesureRepository extends CrudRepository<UniteOfMesure, Long> {

    Optional<UniteOfMesure> findByUom(String description);


}
