package org.example.controller.template;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/photo/{locationType}/{locationName}")
public class LocationController {

    private static final String template = "locationTemplate";

    @GetMapping
    public String get(Model model,
                      @PathVariable("locationType") String locationType,
                      @PathVariable("locationName") String locationName) {

        model.addAttribute("locationType", locationType)
             .addAttribute("locationName", locationName);
        return template;
    }

    @GetMapping("/ajax")
    public String getAjax(Model model,
                          @PathVariable("locationType") String locationType,
                          @PathVariable("locationName") String locationName) {

        model.addAttribute("locationType", locationType)
             .addAttribute("locationName", locationName);
        return Fragment.get(template);
    }
}
