package org.example.controller.image;

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

import java.io.IOException;

@Controller
@RequestMapping("/moderator/image/add")
public class ImageAddController {

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

    @PostMapping
    @ResponseBody
    public String addImageReq(@RequestParam("type") String locationType,
                              @RequestParam("parentName") String parentName,
                              @RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("image") MultipartFile file
    ) throws IOException {

        LoggerFactory.getLogger(this.getClass()).info(locationType + " " + parentName + " " + name + " " + description + " " + file.getSize());

        LocationType type = LocationType.fromString(locationType);

        int id = createLocation(type, name, description, parentName);
        createImage(type, id, file);

        return "redirect:/Photo/" + locationType + "/" + name;
    }

    private int createLocation(LocationType type, String name, String description, String parentName) {
        switch (type) {
            case COUNTRY: return createCountry(name, description);
            case REGION: return createRegion(name, description, parentName);
            case CITY: return createCity(name, description, parentName);
            case ATTRACTION: return createAttraction(name, description, parentName);
            default: throw new IllegalStateException("Unexpected value: " + type.toString());
        }
    }

    private int createCountry(String name, String description) {
        Country country = new Country();
        country.setName(name);
        country.setDescription(description);

        countryStorage.add(country);

        return countryStorage.get(name).getId();
    }

    private int createRegion(String name, String description, String parentName) {
        Country country = countryStorage.get(parentName);

        Region region = new Region();
        region.setName(name);
        region.setDescription(description);
        region.setCountry(country);

        regionStorage.add(region);

        return regionStorage.get(name).getId();
    }

    private int createCity(String name, String description, String parentName) {
        Region region = regionStorage.get(parentName);

        City city = new City();
        city.setName(name);
        city.setDescription(description);
        city.setRegion(region);

        cityStorage.add(city);

        return cityStorage.get(name).getId();
    }

    private int createAttraction(String name, String description, String parentName) {
        City city = cityStorage.get(parentName);

        Attraction attraction = new Attraction();
        attraction.setName(name);
        attraction.setDescription(description);
        attraction.setCity(city);

        attractionStorage.add(attraction);

        return attractionStorage.get(name).getId();
    }

    private void createImage(LocationType locationType, int id, MultipartFile file) throws IOException {
        Image image = new Image();
        image.setLocationType(locationType);
        image.setLocationId(id);
        image.setData(file.getBytes());
        imageStorage.add(image);
    }

}
