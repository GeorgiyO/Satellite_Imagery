package org.example.controller.image;

import org.example.database.attraction.AttractionStorage;
import org.example.database.city.CityStorage;
import org.example.database.country.CountryStorage;
import org.example.database.image.ImageStorage;
import org.example.database.region.RegionStorage;
import org.example.domain.location.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("moderator/image/delete")
public class ImageDeleteController {

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
    public String getDeletingImageForm(Model model) {
        return "deleteImageForm";
    }

    @PostMapping
    @ResponseBody
    public String deleteLocation(@RequestParam("type") String locationType,
                                 @RequestParam("name") String name) {

        LocationType type = LocationType.fromString(locationType);

        switch (type) {
            case COUNTRY:
                deleteCountry(name);
                break;
            case REGION:
                deleteRegion(name);
                break;
            case CITY:
                deleteCity(name);
                break;
            case ATTRACTION:
                deleteAttraction(name);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type.toString());
        }

        return "redirect:/";
    }

    private void deleteCountry(String name) {
        Country country = countryStorage.get(name);
        countryStorage.delete(country.getId());
    }

    private void deleteRegion(String name) {
        Region region = regionStorage.get(name);
        regionStorage.delete(region.getId());
    }

    private void deleteCity(String name) {
        City city = cityStorage.get(name);
        cityStorage.delete(city.getId());
    }

    private void deleteAttraction(String name) {
        Attraction attraction = attractionStorage.get(name);
        attractionStorage.delete(attraction.getId());
    }

}
