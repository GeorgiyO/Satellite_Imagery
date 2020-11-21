package org.example.controller.image;

import org.example.controller.image.form.AddingForm;
import org.example.database.image.ImageStorage;
import org.example.domain.Image;
import org.example.domain.location.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/moderator/image/add")
public class ImageAddController {

    @Autowired
    private ImageStorage imageStorage;

    @Autowired
    private AddingForm addingForm;

    @PostMapping
    @ResponseBody
    public String addImageReq(@RequestParam("type") String locationType,
                              @RequestParam("parentName") String parentName,
                              @RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("image") MultipartFile file
    ) throws IOException {

        LoggerFactory.getLogger(this.getClass()).info(String.format("try add location: %s %s %s %s", locationType, parentName, name, description));
        LoggerFactory.getLogger(this.getClass()).info(String.format("with file: %s %s %s", file.getOriginalFilename(), file.getContentType(), file.getSize()));

        if (!Objects.equals(file.getContentType(), "image/png")) {
            return "imageFormatError";
        }

        LocationType type;
        int id = -1;

        try {
             type = LocationType.fromString(locationType);
        } catch (IllegalStateException e) {
            return "typeError";
        }

        addingForm.setData(type, name, description, parentName);
        if (addingForm.isValid()) {
            try {
                id = addingForm.createLocation();
            } catch (Exception e) {
                return "parentNameError";
            }
        } else {
            return String.valueOf(addingForm.getErrors());
        }

        createImage(type, id, file);

        return "redirect:/photo/" + locationType + "/" + name;
    }


    private void createImage(LocationType locationType, int id, MultipartFile file) throws IOException {
        Image image = new Image();
        image.setLocationType(locationType);
        image.setLocationId(id);
        image.setData(file.getBytes());
        imageStorage.add(image);
    }

}
