package com.example.demo.controller;

import com.example.demo.enitiy.Mapphoto;
import com.example.demo.enitiy.User;
import com.example.demo.service.MapphotoService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    private MapphotoService mapphotoService;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/login/tologin")
    public String tologin(Model model, HttpServletRequest request,HttpServletResponse httpServletResponse){
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user=userService.getUserByName(username);
            if(!password.equals(user.getPassword())){
                model.addAttribute("logmsg","密码错误");
                return "login";
            }

            Cookie cookie=new Cookie("user",username);
            model.addAttribute("user",username);
            httpServletResponse.addCookie(cookie);
            List<Mapphoto> mapphotoList=mapphotoService.mapphotoList(username);
            if(mapphotoList.size()>0){
                model.addAttribute("maplist",mapphotoList);
            }


            return "controllerblock";
        }catch (NullPointerException e){
            model.addAttribute("logmsg","用户名不存在");
            return "login";
        }
    }
}
