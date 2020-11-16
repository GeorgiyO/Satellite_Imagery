package org.example.controller;

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

@Controller
@RequestMapping("moderator/image/update")
public class ImageUpdateController {

    @Autowired
    private ImageStorage imageStorage;
    @Autowired
    private CountryStorage countryStorage;
    @Autowired
    private RegionStorage regionStorage;
    @Autowired
    private CityStorage cityStorage;
    @Autowired
    private AttractionStorage attractionStorage;

    @GetMapping
    public String getUpdatingImageForm(Model model) {
        return "updateImageTemplate";
    }

    @PostMapping
    public String updateImageReq(@RequestParam("type") String locationType,
                                 @RequestParam("name") String name,
                                 @RequestParam("parentName") String parentName,
                                 @RequestParam("newName") String newName,
                                 @RequestParam("description") String description,
                                 @RequestParam("image") MultipartFile file,
                                 HttpServletResponse response) throws IOException {

        LocationType type = LocationType.fromString(locationType);

        int id = updateLocation(type, name, parentName, newName, description);

        if (!file.isEmpty()) {
            updateFile(type, id, file);
        }

        String redirect = "redirect:/" + locationType + "/";
        redirect += isEmpty(newName) ? name : newName;

        return redirect;
    }

    private int updateLocation(LocationType type, String name, String parentName, String newName, String description) {
        switch (type) {
            case COUNTRY: return updateCountry(name, newName, description);
            case REGION: return updateRegion(name, newName, description, parentName);
            case CITY: return updateCity(name, newName, description, parentName);
            case ATTRACTION: return updateAttraction(name, newName, description, parentName);
            default: throw new IllegalStateException("Unexpected value: " + type.toString());
        }
    }

    private int updateCountry(String name, String newName, String description) {
        Country country = countryStorage.get(name);
        if (!isEmpty(newName)) country.setName(newName);
        if (!isEmpty(description)) country.setDescription(description);
        countryStorage.update(country);
        return country.getId();
    }

    private int updateRegion(String name, String newName, String description, String parentName) {
        Region region = regionStorage.get(name);
        if (!isEmpty(newName)) region.setName(newName);
        if (!isEmpty(description)) region.setDescription(description);
        if (!isEmpty(parentName)) region.setCountry(countryStorage.get(parentName));
        regionStorage.update(region);
        return region.getId();
    }

    private int updateCity(String name, String newName, String description, String parentName) {

        LoggerFactory.getLogger("ХУЙ ХУЙ ХУЙ ХУЙ ХУЙ ХУЙ").info(String.format("%s %s %s %s", name, newName, description, parentName));

        City city = cityStorage.get(name);
        if (!isEmpty(newName)) city.setName(newName);
        if (!isEmpty(description)) city.setDescription(description);
        if (!isEmpty(parentName)) city.setRegion(regionStorage.get(parentName));
        cityStorage.update(city);
        return city.getId();
    }

    private int updateAttraction(String name, String newName, String description, String parentName) {
        Attraction attraction = attractionStorage.get(name);
        if (!isEmpty(newName)) attraction.setName(newName);
        if (!isEmpty(description)) attraction.setDescription(description);
        if (!isEmpty(parentName)) attraction.setCity(cityStorage.get(parentName));
        attractionStorage.update(attraction);
        return attraction.getId();
    }

    private boolean isEmpty(String field) {
        return field.strip().length() == 0;
    }

    private void updateFile(LocationType type, int locationId, MultipartFile file) throws IOException {
        Image image = new Image();
        image.setLocationType(type);
        image.setLocationId(locationId);
        image.setData(file.getBytes());
        imageStorage.update(image);
    }

}
