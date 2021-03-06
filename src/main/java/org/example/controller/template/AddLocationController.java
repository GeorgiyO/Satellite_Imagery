package org.example.controller.template;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/moderator/image/add/{type}/{parentName}")
public class AddLocationController {
    private static final String template = "addLocationTemplate";

    @GetMapping
    public String get(Model model,
                      @PathVariable("type") String type,
                      @PathVariable("parentName") String parentName) {

        model.addAttribute("locationType", type)
                .addAttribute("parentName", parentName);
        return template;
    }

    @GetMapping("/ajax")
    public String getAjax(Model model,
                          @PathVariable("type") String type,
                          @PathVariable("parentName") String parentName) {

        model.addAttribute("locationType", type)
                .addAttribute("parentName", parentName);
        return Fragment.get(template);
    }
}
