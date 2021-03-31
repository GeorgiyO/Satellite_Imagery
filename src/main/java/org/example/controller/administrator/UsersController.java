package org.example.controller.administrator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.example.controller.template.Fragment;
import org.example.database.user.UserService;
import org.example.entity.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/administrator/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String usersPanel() {
        return "adm/users-panel";
    }
    @GetMapping("/ajax")
    public String usersPanelAjax() {
        return Fragment.get("adm/users-panel");
    }

    @PostMapping("/get-list")
    @ResponseBody
    public String getUserList(@RequestParam("username") String username) {

        List<User> userList = userService.findByNameContaining(username);
        JsonArray jsonUserList = new JsonArray();

        for (var user : userList) {
            if (user.getId() <= 2) continue;

            JsonObject jsonUser = new JsonObject();
            jsonUser.addProperty("id", user.getId());
            jsonUser.addProperty("name", user.getName());

            JsonArray roles = new JsonArray();
            for (var role : user.getRoleList()) {
                roles.add(role.getAuthority());
            }
            jsonUser.add("roles", roles);
            jsonUserList.add(jsonUser);
        }

        return jsonUserList.toString();
    }

    @PostMapping("/delete")
    @ResponseBody
    public String deleteById(@RequestParam("userId") long userId) {
        LoggerFactory.getLogger(this.getClass()).info("try to delete user: " + userId);

        if (userId <= 2) return "not-exist";
        try {
            userService.deleteById(userId);
            return "succeed";
        } catch (EmptyResultDataAccessException e) {
            return "not-exist";
        }
    }

    @PostMapping("/set-role")
    @ResponseBody
    public String setUserRole(@RequestParam("userId") long userId,
                              @RequestParam("userRole") String role) {
        try {
            userService.changeRole(userId, role);
            return "succeed";
        } catch (Exception e) {
            return "failure";
        }
    }
}
