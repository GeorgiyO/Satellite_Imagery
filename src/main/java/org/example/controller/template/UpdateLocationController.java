package org.example.controller.template;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/moderator/image/update/{type}/{oldName}")
public class UpdateLocationController {

    private static final String template = "updateLocationTemplate";

    @GetMapping
    public String get(Model model,
                      @PathVariable("type") String type,
                      @PathVariable("oldName") String oldName) {

        model.addAttribute("locationType", type)
                .addAttribute("oldName", oldName);
        return template;
    }

    @GetMapping("/ajax")
    public String getAjax(Model model,
                          @PathVariable("type") String type,
                          @PathVariable("oldName") String oldName) {

        model.addAttribute("locationType", type)
                .addAttribute("oldName", oldName);
        return Fragment.get(template);
    }
}
