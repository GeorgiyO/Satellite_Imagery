package org.example.controller.image;

import org.example.controller.image.form.UpdatingForm;
import org.example.database.attraction.AttractionStorage;
import org.example.database.city.CityStorage;
import org.example.database.country.CountryStorage;
import org.example.database.image.ImageStorage;
import org.example.database.region.RegionStorage;
import org.example.domain.Image;
import org.example.domain.location.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("moderator/image/update")
public class ImageUpdateController {

    @Autowired
    private ImageStorage imageStorage;

    @Autowired
    private UpdatingForm updatingForm;

    @PostMapping
    @ResponseBody
    public String updateImageReq(@RequestParam("type") String locationType,
                                 @RequestParam("name") String name,
                                 @RequestParam("parentName") String parentName,
                                 @RequestParam("newName") String newName,
                                 @RequestParam("description") String description,
                                 @RequestParam("image") MultipartFile file,
                                 HttpServletResponse response) throws IOException {

        LoggerFactory.getLogger(this.getClass()).info(String.format("try update location: %s %s %s %s %s", locationType, name, parentName, newName, description));
        LoggerFactory.getLogger(this.getClass()).info(String.format("with file: %s %s %s", file.getOriginalFilename(), file.getContentType(), file.getSize()));

        if (!file.isEmpty() && !Objects.equals(file.getContentType(), "image/png")) {
            return "imageFormatError";
        }

        LocationType type;
        int id = -1;

        try {
            type = LocationType.fromString(locationType);
        } catch (IllegalStateException e) {
            return "typeError";
        }

        updatingForm.setData(type, name, parentName, newName , description);

        if (updatingForm.isValid()) {
            try {
                id = updatingForm.updateLocation();
            } catch (Exception e) {
                return "uncheckedFieldsError";
            }
        } else {
            return String.valueOf(updatingForm.getErrors());
        }

        if (!file.isEmpty()) {
            updateFile(type, id, file);
        }

        String redirect = "redirect:/Photo/" + locationType + "/";
        redirect += newName.strip().length() == 0 ? name : newName;

        return redirect;
    }

    private void updateFile(LocationType type, int locationId, MultipartFile file) throws IOException {
        Image image = new Image();
        image.setLocationType(type);
        image.setLocationId(locationId);
        image.setData(file.getBytes());
        imageStorage.update(image);
    }

}
