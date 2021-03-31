package org.example.controller.administrator;

import org.example.controller.template.Fragment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrator")
public class PanelController {

    @GetMapping
    public String admPanel() {
        return "adm/panel";
    }

    @GetMapping("/ajax")
    public String admPanelAjax() {
        return Fragment.get("adm/panel");
    }

}
