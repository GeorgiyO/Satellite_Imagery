package org.example.controller.image.form;

import com.google.gson.JsonObject;
import org.example.database.attraction.AttractionStorage;
import org.example.database.city.CityStorage;
import org.example.database.country.CountryStorage;
import org.example.database.region.RegionStorage;
import org.example.domain.location.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.regex.Pattern;

public class AddingForm {

    private static final Pattern pattern = Pattern.compile("^[A-Za-zА-Яа-я, _-]+");
    public static final String patterErrorMsg = "Поле должно содержать только русские и английские буквы, запятую, знак пробела, дефис, знак нижнего подчеркивания";

    @Autowired
    private CountryStorage countryStorage;
    @Autowired
    private RegionStorage regionStorage;
    @Autowired
    private CityStorage cityStorage;
    @Autowired
    private AttractionStorage attractionStorage;

    private LocationType type;
    private String name;
    private String description;
    private String parentName;
    private byte[] image;

    private JsonObject errors;

    public void setData(LocationType type, String name, String description, String parentName, MultipartFile file) throws IOException {

        errors = new JsonObject(); // пришли новые данные значит ошибки нужно сбросить

        this.type = type;
        this.name = name;
        this.description = description;
        this.parentName = parentName;
        this.image = file.getBytes();
    }

    public boolean isValid() {
        boolean isValid = true;

        if (fieldIsEmpty(name)) {
            errors.addProperty("name", "Пустое поле");
            isValid = false;
        } else if (fieldIsNotMatches(name)) {
            errors.addProperty("name", patterErrorMsg);
            isValid = false;
        }

        if (fieldIsEmpty(description)) {
            errors.addProperty("description", "Пустое поле");
            isValid = false;
        } else if (fieldIsNotMatches(description)) {
            errors.addProperty("description", patterErrorMsg);
            isValid = false;
        }

        return isValid;
    }

    private boolean fieldIsNotMatches(String field) {
        return !pattern.matcher(field).matches();
    }

    private boolean fieldIsEmpty(String field) {
        return field.strip().length() == 0;
    }

    public JsonObject getErrors() {
        return errors;
    }

    public void createLocation() {
        switch (type) {
            case COUNTRY: createCountry(); return;
            case REGION: createRegion(); return;
            case CITY: createCity(); return;
            case ATTRACTION: createAttraction(); return;
            default: throw new IllegalStateException("Unexpected value: " + type.toString());
        }
    }

    private void createCountry() {
        Country country = new Country();
        country.setName(name);
        country.setDescription(description);

        countryStorage.add(country, image);
    }

    private void createRegion() {
        Country country = countryStorage.get(parentName);

        Region region = new Region();
        region.setName(name);
        region.setDescription(description);
        region.setCountry(country);

        regionStorage.add(region, image);
    }

    private void createCity() {
        Region region = regionStorage.get(parentName);

        City city = new City();
        city.setName(name);
        city.setDescription(description);
        city.setRegion(region);

        cityStorage.add(city, image);
    }

    private void createAttraction() {
        City city = cityStorage.get(parentName);

        Attraction attraction = new Attraction();
        attraction.setName(name);
        attraction.setDescription(description);
        attraction.setCity(city);

        attractionStorage.add(attraction, image);
    }
}
