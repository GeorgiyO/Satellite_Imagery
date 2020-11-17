package org.example.controller.template;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/moderator/image/delete/{type}/{name}")
public class ImageDeleteTemplate {

    private static final String template = "deleteLocationTemplate";

    @GetMapping
    public String get(Model model,
                      @PathVariable("type") String type,
                      @PathVariable("name") String name) {

        model.addAttribute("type", type)
                .addAttribute("name", name);
        return template;
    }

    @GetMapping("/ajax")
    public String getAjax(Model model,
                          @PathVariable("type") String type,
                          @PathVariable("name") String name) {

        model.addAttribute("type", type)
                .addAttribute("name", name);
        return Fragment.get(template);
    }
}
