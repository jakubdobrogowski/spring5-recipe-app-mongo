package guru.springframework.spring5recipeapp.bootstrap;


import guru.springframework.spring5recipeapp.model.Category;
import guru.springframework.spring5recipeapp.model.UniteOfMesure;
import guru.springframework.spring5recipeapp.repositories.CategoryRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMesureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 8/7/17.
 */
@Slf4j
@Component
@Profile({"dev", "prod"})
public class BootstrapMySQL implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMesureRepository unitOfMeasureRepository;

    public BootstrapMySQL(CategoryRepository categoryRepository,
                          UnitOfMesureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (categoryRepository.count() == 0L){
            log.debug("Loading Categories");
            loadCategories();
        }

        if (unitOfMeasureRepository.count() == 0L){
            log.debug("Loading UOMs");
            loadUom();
        }
    }

    private void loadCategories(){
        Category cat1 = new Category();
        cat1.setCategoryName("American");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setCategoryName("Italian");
        categoryRepository.save(cat2);

        Category cat3 = new Category();
        cat3.setCategoryName("Mexican");
        categoryRepository.save(cat3);

        Category cat4 = new Category();
        cat4.setCategoryName("Fast Food");
        categoryRepository.save(cat4);
    }

    private void loadUom(){
        UniteOfMesure uom1 = new UniteOfMesure();
        uom1.setUom("Teaspoon");
        unitOfMeasureRepository.save(uom1);

        UniteOfMesure uom2 = new UniteOfMesure();
        uom2.setUom("Tablespoon");
        unitOfMeasureRepository.save(uom2);

        UniteOfMesure uom3 = new UniteOfMesure();
        uom3.setUom("Cup");
        unitOfMeasureRepository.save(uom3);

        UniteOfMesure uom4 = new UniteOfMesure();
        uom4.setUom("Pinch");
        unitOfMeasureRepository.save(uom4);

        UniteOfMesure uom5 = new UniteOfMesure();
        uom5.setUom("Ounce");
        unitOfMeasureRepository.save(uom5);

        UniteOfMesure uom6 = new UniteOfMesure();
        uom6.setUom("Each");
        unitOfMeasureRepository.save(uom6);

        UniteOfMesure uom7 = new UniteOfMesure();
        uom7.setUom("Pint");
        unitOfMeasureRepository.save(uom7);

        UniteOfMesure uom8 = new UniteOfMesure();
        uom8.setUom("Dash");
        unitOfMeasureRepository.save(uom8);
    }
}