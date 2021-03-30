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

public class UpdatingForm {

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
    private String parentName;
    private String newName;
    private String description;
    private byte[] image;

    private JsonObject errors;

    public void setData(LocationType type, String name, String parentName, String newName, String description, MultipartFile file) throws IOException {

        errors = new JsonObject(); // пришли новые данные значит ошибки нужно сбросить

        this.type = type;
        this.name = name;
        this.parentName = parentName;
        this.newName = newName;
        this.description = description;
        this.image = file.getBytes();
    }

    public boolean isValid() {
        boolean isValid = true;

        if (fieldIsNotEmpty(parentName) && fieldIsNotMatches(parentName)) {
            errors.addProperty("parentName", patterErrorMsg);
            isValid = false;
        }
        if (fieldIsNotEmpty(newName) && fieldIsNotMatches(newName)) {
            errors.addProperty("newName", patterErrorMsg);
            isValid = false;
        }
        if (fieldIsNotEmpty(description) && fieldIsNotMatches(description)) {
            errors.addProperty("description", patterErrorMsg);
            isValid = false;
        }

        return isValid;
    }

    private boolean fieldIsNotMatches(String field) {
        return !pattern.matcher(field).matches();
    }

    private boolean fieldIsNotEmpty(String field) {
        return field.strip().length() != 0;
    }

    public JsonObject getErrors() {
        return errors;
    }

    public void updateLocation() {
        switch (type) {
            case COUNTRY: updateCountry(); break;
            case REGION: updateRegion(); break;
            case CITY: updateCity(); break;
            case ATTRACTION: updateAttraction(); break;
            default: throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private void updateCountry() {
        Country country = countryStorage.get(name);

        if (fieldIsNotEmpty(newName)) country.setName(newName);
        if (fieldIsNotEmpty(description)) country.setDescription(description);

        countryStorage.update(country, image);
    }

    private void updateRegion() {
        Region region = regionStorage.get(name);

        if (fieldIsNotEmpty(parentName)) region.setCountry(countryStorage.get(parentName));

        if (fieldIsNotEmpty(newName)) region.setName(newName);
        if (fieldIsNotEmpty(description)) region.setDescription(description);

        regionStorage.update(region, image);
    }

    private void updateCity() {
        City city = cityStorage.get(name);

        if (fieldIsNotEmpty(parentName)) city.setRegion(regionStorage.get(parentName));

        if (fieldIsNotEmpty(newName)) city.setName(newName);
        if (fieldIsNotEmpty(description)) city.setDescription(description);

        cityStorage.update(city, image);
    }

    private void updateAttraction() {
        Attraction attraction = attractionStorage.get(name);

        if (fieldIsNotEmpty(parentName)) attraction.setCity(cityStorage.get(parentName));

        if (fieldIsNotEmpty(description)) attraction.setDescription(description);
        if (fieldIsNotEmpty(newName)) attraction.setName(newName);

        attractionStorage.update(attraction, image);
    }
}
