package org.example.controller.template;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private static final String template = "index";

    @GetMapping("/")
    public String get(Model model) {
        return template;
    }

    @GetMapping("/ajax")
    public String getAjax(Model model) {
        return Fragment.get(template);
    }

}
