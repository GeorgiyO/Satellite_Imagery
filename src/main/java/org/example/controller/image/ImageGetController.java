package org.example.controller.image;

import com.google.gson.Gson;
import org.example.database.attraction.AttractionStorage;
import org.example.database.city.CityStorage;
import org.example.database.country.CountryStorage;
import org.example.database.image.ImageStorage;
import org.example.database.region.RegionStorage;
import org.example.domain.Image;
import org.example.domain.location.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
public class ImageGetController {

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

    @Autowired
    private Gson JSON;

    @GetMapping(value = "/image/{locationType}/{locationId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImageByCountry(
            @PathVariable String locationType,
            @PathVariable int locationId) throws SQLException {

        Image image = imageStorage.get(LocationType.fromString(locationType), locationId);
        byte[] data = image.getData();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @GetMapping("/location/{locationType}/{locationName}")
    @ResponseBody
    public String getJSONLocation(
            @PathVariable String locationType,
            @PathVariable String locationName) {

        return JSON.toJson(getObjectLocation(LocationType.fromString(locationType), locationName));
    }

    @GetMapping("/list/{childType}/{parentName}")
    @ResponseBody
    public String getJSONChildList(
            @PathVariable String childType,
            @PathVariable String parentName) {

        return JSON.toJson(getObjectChildList(LocationType.fromString(childType), parentName));
    }

    private Object getObjectLocation(LocationType type, String name) {
        switch (type) {
            case COUNTRY: return countryStorage.get(name);
            case REGION: return regionStorage.get(name);
            case CITY: return cityStorage.get(name);
            case ATTRACTION: return attractionStorage.get(name);
            default: throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private Object getObjectChildList(LocationType childType, String parentName) {
        switch (childType) {
            case COUNTRY: return countryStorage.getList();
            case REGION: return regionStorage.getList(countryStorage.get(parentName).getLocationId());
            case CITY: return cityStorage.getList(regionStorage.get(parentName).getLocationId());
            case ATTRACTION: return attractionStorage.getList(cityStorage.get(parentName).getLocationId());
            default: throw new IllegalStateException("Unexpected value: " + childType);
        }
    }
}
