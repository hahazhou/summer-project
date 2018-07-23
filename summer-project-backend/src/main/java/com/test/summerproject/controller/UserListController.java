package com.test.summerproject.controller;


import com.test.summerproject.enitiy.User;
import com.test.summerproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserListController {
    @Autowired
    private UserService userService;

    @RequestMapping("/userInfo")
    public String userInfo(Model model) {
        try {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            return "userlist";
        } catch (EmptyResultDataAccessException e) {

            model.addAttribute("errmsg", "list is empty");
            return "error";
        }

    }
}
