package guru.springframework.spring5recipeapp.service.implementations;

import guru.springframework.spring5recipeapp.model.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.service.interfaces.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImage(Long id, MultipartFile multipartFile) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        Recipe recipe = null;
        if (!recipeOptional.isPresent()) {

            log.error("Not Found Recipe"); //todo handle better way
        } else {
            recipe = recipeOptional.get();
        }
        try {

            Byte[] bytes = new Byte[multipartFile.getBytes().length];

            int i = 0;
            for (Byte aByte : multipartFile.getBytes()) {
                bytes[i++] = aByte;
            }
            if(recipe !=null){

                recipe.setImage(bytes);
            }
            recipeRepository.save(recipe);
        } catch (IOException e) {

            log.error("Error occured " + e );//todo handle better way
            e.printStackTrace();
        }


    }
}
