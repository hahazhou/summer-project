package com.test.summerproject.controller;

import com.test.summerproject.enitiy.User;
import com.test.summerproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class RegisterController {
    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/insertUser")
    public String insertUser(Model model, HttpServletRequest request) {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String repassword = request.getParameter("repassword");
            User user = userService.getUserByName(username);
            if (!password.equals(repassword)) {
                model.addAttribute("msg", "两次密码不一致");
                return "register";
            }
            if (Objects.isNull(user)) {
                user = new User();
                user.setUsername(username);
                user.setPassword(password);
                userService.insertUser(user);
                model.addAttribute("msg", "注册成功");
                return "register";
            }

            model.addAttribute("msg", "用户名已存在");
            return "register";

        } catch (EmptyResultDataAccessException e) {
            User user = new User();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            user.setUsername(username);
            user.setPassword(password);
            userService.insertUser(user);
            model.addAttribute("msg", "注册成功");
            return "register";
        }

    }

    ;


}
