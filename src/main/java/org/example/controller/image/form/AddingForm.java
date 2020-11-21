package org.example.controller.image.form;

import com.google.gson.JsonObject;
import org.example.database.attraction.AttractionStorage;
import org.example.database.city.CityStorage;
import org.example.database.country.CountryStorage;
import org.example.database.region.RegionStorage;
import org.example.domain.location.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

public class AddingForm {

    private static final Pattern pattern = Pattern.compile("^[A-Za-zА-Яа-я, ]+");

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

    private JsonObject errors;

    public void setData(LocationType type, String name, String description, String parentName) {

        errors = new JsonObject(); // пришли новые данные значит ошибки нужно сбросить

        this.type = type;
        this.name = name;
        this.description = description;
        this.parentName = parentName;
    }

    public boolean isValid() {
        boolean isValid = true;

        if (fieldIsEmpty(name)) {
            errors.addProperty("name", "Пустое поле");
            isValid = false;
        } else if (!fieldIsMatches(name)) {
            errors.addProperty("name", "Поле должно содержать только русские и латинские символы, запятую, знак пробела");
            isValid = false;
        }

        if (fieldIsEmpty(description)) {
            errors.addProperty("description", "Пустое поле");
            isValid = false;
        } else if (!fieldIsMatches(description)) {
            errors.addProperty("description", "Поле должно содержать только русские и латинские символы, запятую, знак пробела");
            isValid = false;
        }

        return isValid;
    }

    private boolean fieldIsMatches(String field) {
        return pattern.matcher(field).matches();
    }

    private boolean fieldIsEmpty(String field) {
        return field.strip().length() == 0;
    }

    public JsonObject getErrors() {
        return errors;
    }

    public int createLocation() {
        switch (type) {
            case COUNTRY: return createCountry();
            case REGION: return createRegion();
            case CITY: return createCity();
            case ATTRACTION: return createAttraction();
            default: throw new IllegalStateException("Unexpected value: " + type.toString());
        }
    }

    private int createCountry() {
        Country country = new Country();
        country.setName(name);
        country.setDescription(description);

        countryStorage.add(country);

        return countryStorage.get(name).getId();
    }

    private int createRegion() {
        Country country = countryStorage.get(parentName);

        Region region = new Region();
        region.setName(name);
        region.setDescription(description);
        region.setCountry(country);

        regionStorage.add(region);

        return regionStorage.get(name).getId();
    }

    private int createCity() {
        Region region = regionStorage.get(parentName);

        City city = new City();
        city.setName(name);
        city.setDescription(description);
        city.setRegion(region);

        cityStorage.add(city);

        return cityStorage.get(name).getId();
    }

    private int createAttraction() {
        City city = cityStorage.get(parentName);

        Attraction attraction = new Attraction();
        attraction.setName(name);
        attraction.setDescription(description);
        attraction.setCity(city);

        attractionStorage.add(attraction);

        return attractionStorage.get(name).getId();
    }
}
