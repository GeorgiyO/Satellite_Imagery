package org.example.controller;

import org.example.database.country.CountryStorage;
import org.example.database.image.ImageStorage;
import org.example.database.region.RegionStorage;
import org.example.domain.Image;
import org.example.domain.location.Country;
import org.example.domain.location.Location;
import org.example.domain.location.LocationType;
import org.example.domain.location.Region;
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
public class ImageGetController {

    @Autowired
    private ImageStorage imageStorage;
    @Autowired
    private CountryStorage countryStorage;
    @Autowired
    private RegionStorage regionStorage;

    @GetMapping(value = "image/{locationType}/{locationId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImageByCountry(
            @PathVariable String locationType,
            @PathVariable int locationId) throws SQLException {

        Image image = imageStorage.get(LocationType.fromString(locationType), locationId);
        byte[] data = image.getData();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @GetMapping("{locationType}/{locationName}")
    public String getImagePage(
            Model model,
            @PathVariable String locationType,
            @PathVariable String locationName) {

        setAttributes(model, LocationType.fromString(locationType), locationName);
        return "imageTemplate";
    }

    private void setAttributes(Model model, LocationType type, String name) {
        switch (type) {
            case COUNTRY:
                setCountryAttributes(model, name);
                break;
            case REGION:
                setRegionAttributes(model, name);
                break;
        }
    }

    private void setCountryAttributes(Model model, String name) {
        Country country = countryStorage.get(name);
        model.addAttribute("location", country)
             .addAttribute("type", "Country");
    }

    private void setRegionAttributes(Model model, String name) {
        Region region = regionStorage.get(name);
        model.addAttribute("location", region)
                .addAttribute("type", "Region")
                .addAttribute("parentType", "Country")
                .addAttribute("parentName", region.getCountry().getName());
    }
}
