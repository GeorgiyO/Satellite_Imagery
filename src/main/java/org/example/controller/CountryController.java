package org.example.controller;

import org.example.database.image.country.CountryStorage;
import org.example.domain.image.Country;
import org.slf4j.LoggerFactory;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;


@Controller
public class CountryController {
    @Autowired
    private CountryStorage countryStorage;

    @GetMapping(value = "country/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImageByCountry(@PathVariable int id) throws SQLException {

        byte[] imageByteArr = countryStorage.get(id).getImage();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageByteArr, headers, HttpStatus.OK);
    }

    @GetMapping("country/{name}")
    public String getPage(Model model, @PathVariable String name) {
        Country country = countryStorage.get(name);
        model.addAttribute("country", country);
        return "imageTemplate";
    }

    @GetMapping("moderator/image/add")
    public String getAddingImageForm(Model model) {
        return "addImageTemplate";
    }

    @PostMapping("moderator/image/add")
    public String addCountry(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("image") MultipartFile image) throws IOException {

        Country country = new Country();
        country.setName(name);
        country.setDescription(description);
        country.setImage(image.getBytes());
        countryStorage.add(country);

        return "redirect:/country/" + name;
    }
}
