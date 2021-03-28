package org.example.controller.template;

import org.example.database.country.CountryStorage;
import org.example.domain.location.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    CountryStorage cs;

    private static final String template = "index";

    @GetMapping("/")
    public String get(Model model) {
        var c = new Country();
        c.setName("asdhgasdfasdfggf");
        c.setDescription("asjfdkjfalks");
        cs.add(c);
        System.out.println(cs.get(0));
        return template;
    }

    @GetMapping("/ajax")
    public String getAjax(Model model) {
        return Fragment.get(template);
    }

}
