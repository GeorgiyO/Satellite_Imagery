package org.example.controller;

import org.example.controller.template.Fragment;
import org.example.database.user.UserService;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Pattern;

@Controller
public class SecurityController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("error", false);
        return "authentification";
    }

    @GetMapping("/login/ajax")
    public String loginAjax() {
        return Fragment.get("authentification");
    }

    @GetMapping("/failure")
    public String failure(Model model) {
        model.addAttribute("error", true);
        return "authentification";
    }


    @PostMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("error", "");
        return "registration";
    }

    @GetMapping("/registration/ajax")
    public String registrationAjax(Model model) {
        model.addAttribute("error", "");
        return Fragment.get("registration");
    }

    @PostMapping("/registration")
    public String createUser(Model model, @RequestParam String name, @RequestParam String password) {

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");
        if (!pattern.matcher(name).matches()) {
            model.addAttribute("error", "Имя пользователя должно состоять из латинских букв и цифр");
            return "registration";
        }
        if (password.length() < 8) {
            model.addAttribute("error", "Пароль должен состоять как минимум из 8-ми символов");
            return "registration";
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        try {
            userService.add(user);
            return "redirect:/login";
        } catch (IllegalStateException e) {
            model.addAttribute("error", "Пользователь уже существует");
            return "registration";
        }
    }

}
