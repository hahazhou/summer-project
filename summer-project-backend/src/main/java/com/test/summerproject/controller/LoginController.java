package com.test.summerproject.controller;

import com.test.summerproject.enitiy.User;
import com.test.summerproject.service.MapphotoService;
import com.test.summerproject.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private MapphotoService mapphotoService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login/tologin")
    @ResponseBody
    public Object tologin(HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            System.out.print(username);
            System.out.print(password);
            User user = userService.getUserByName(username);
            JSONObject data = new JSONObject();
            if (!password.equals(user.getPassword())) {

                data.put("msg", "密码错误");
                return data.toString();
            }

            data.put("msg", "登录成功");
            return data.toString();
        } catch (NullPointerException e) {

            return new JSONObject().put("msg", "用户不存在").toString();
        }
    }
}
