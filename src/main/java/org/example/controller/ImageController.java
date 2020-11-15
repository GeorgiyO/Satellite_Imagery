package org.example.controller;

import org.example.database.country.CountryStorage;
import org.example.database.image.ImageStorage;
import org.example.domain.Image;
import org.example.domain.location.Country;
import org.example.domain.location.LocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@Controller
public class ImageController {

    @Autowired
    private ImageStorage imageStorage;
    @Autowired
    private CountryStorage countryStorage;

    @GetMapping(value = "image/{location_type}/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImageByCountry(
            @PathVariable String locationType,
            @PathVariable int locationId) throws SQLException {

        Image image = imageStorage.get(LocationType.fromString(locationType), locationId);
        byte[] data = image.getData();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }


    @GetMapping("moderator/image/add")
    public String getAddingImageForm(Model model) {
        return "addImageTemplate";
    }

    @PostMapping("moderator/image/add")
    public String addImage(@RequestParam("type") String locationType,
                           @RequestParam("parentName") String parentName,
                           @RequestParam("name") String name,
                           @RequestParam("description") String description,
                           @RequestParam("image") MultipartFile image
                           ) {

        LocationType type = LocationType.fromString(locationType);

        switch (type) {
            case COUNTRY -> {}
            case REGION -> {}
            case CITY -> {}
            case ATTRACTION -> {}
            default -> throw new IllegalStateException("Unexpected value: " + type.toString());
        }

        return "redirect:/" + locationType + "/" + name;
    }

    private void createCountryImage() {

    }
}
