package org.example.controller;

import org.example.database.city.CityStorage;
import org.example.database.country.CountryStorage;
import org.example.database.image.ImageStorage;
import org.example.database.region.RegionStorage;
import org.example.domain.Image;
import org.example.domain.location.City;
import org.example.domain.location.Country;
import org.example.domain.location.LocationType;
import org.example.domain.location.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("moderator/image/add")
public class ImageAddController {

    @Autowired
    private ImageStorage imageStorage;
    @Autowired
    private CountryStorage countryStorage;
    @Autowired
    private RegionStorage regionStorage;
    @Autowired
    private CityStorage cityStorage;

    @GetMapping
    public String getAddingImageForm(Model model) {
        return "addImageTemplate";
    }

    @PostMapping
    public String addImage(@RequestParam("type") String locationType,
                           @RequestParam("parentName") String parentName,
                           @RequestParam("name") String name,
                           @RequestParam("description") String description,
                           @RequestParam("image") MultipartFile file
    ) throws IOException {

        LocationType type = LocationType.fromString(locationType);

        int id = createLocation(type, name, description, parentName);
        createImage(type, id, file);

        return "redirect:/" + locationType + "/" + name;
    }

    private int createLocation(LocationType type, String name, String description, String parentName) {
        switch (type) {
            case COUNTRY: return createCountry(name, description);
            case REGION: return createRegion(name, description, parentName);
            case CITY: return createCity(name, description, parentName);
            case ATTRACTION: return 0;
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

    private void createImage(LocationType locationType, int id, MultipartFile file) throws IOException {
        Image image = new Image();
        image.setLocationType(locationType);
        image.setLocationId(id);
        image.setData(file.getBytes());
        imageStorage.add(image);
    }

}
