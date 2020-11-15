package org.example.controller;

import org.example.database.country.CountryStorage;
import org.example.domain.Image;
import org.example.domain.location.Country;
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
public class CountryController {
    @Autowired
    private CountryStorage countryStorage;

    @GetMapping("country/{name}")
    public String getPage(Model model, @PathVariable String name) {
        Country country = countryStorage.get(name);
        model.addAttribute("country", country);
        return "imageTemplate";
    }
}
