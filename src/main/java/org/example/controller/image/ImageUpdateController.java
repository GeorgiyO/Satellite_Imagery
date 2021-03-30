package org.example.controller.image;

import org.example.controller.image.form.UpdatingForm;
import org.example.database.image.ImageStorage;
import org.example.domain.location.LocationType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
                                 @RequestParam("image") MultipartFile file) throws IOException {

        LoggerFactory.getLogger(this.getClass()).info(String.format("try update location: %s %s %s %s %s", locationType, name, parentName, newName, description));
        LoggerFactory.getLogger(this.getClass()).info(String.format("with file: %s %s %s", file.getOriginalFilename(), file.getContentType(), file.getSize()));

        if (!file.isEmpty() && !Objects.equals(file.getContentType(), "image/png")) {
            return "imageFormatError";
        }

        LocationType type;

        try {
            type = LocationType.fromString(locationType);
        } catch (IllegalStateException e) {
            return "typeError";
        }

        updatingForm.setData(type, name, parentName, newName , description, file);

        if (updatingForm.isValid()) {
            try {
                updatingForm.updateLocation();
            } catch (Exception e) {
                return "uncheckedFieldsError";
            }
        } else {
            return String.valueOf(updatingForm.getErrors());
        }

        String redirect = "redirect:/photo/" + locationType + "/";
        redirect += newName.strip().length() == 0 ? name : newName;

        return redirect;
    }

}
