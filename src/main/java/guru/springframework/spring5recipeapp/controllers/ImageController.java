package guru.springframework.spring5recipeapp.controllers;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.service.interfaces.ImageService;
import guru.springframework.spring5recipeapp.service.interfaces.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {


    private ImageService imageService;
    private RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{recipeId}/image")
    public String getImageForm(@PathVariable String recipeId, Model model) {

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile multipartFile) {

        imageService.saveImage(Long.valueOf(recipeId), multipartFile);

        return "redirect:/recipe/" + recipeId + "/show";
    }

    @GetMapping("recipe/{recipeId}/recipeimage")
    public void renderImageFromDataBase(@PathVariable String recipeId, HttpServletResponse response) {

        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

            byte[] bytes = new byte[recipeCommand.getImage().length];

            int i = 0;
            for (Byte aByte : recipeCommand.getImage()) {

                bytes[i++] = aByte;
            }

            response.setContentType("image/jpeg");
            InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            try {
                IOUtils.copy(byteArrayInputStream, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
