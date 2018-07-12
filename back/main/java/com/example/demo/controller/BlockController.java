package com.example.demo.controller;

import com.example.demo.enitiy.Mapphoto;
import com.example.demo.service.MapphotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/controllerblock")
public class BlockController {
    @Autowired
    private MapphotoService mapphotoService;

    @RequestMapping("/")
    public String showmap(Model model ,HttpServletRequest request, HttpServletResponse httpServletResponse){
        String username="粥西奥";
        model.addAttribute("user",username);
        List<Mapphoto> mapphotoList=mapphotoService.mapphotoList(username);
        model.addAttribute("maplist",mapphotoList);
        model.addAttribute("mappath", "C:/Users/asus/Pictures/Saved Pictures/map.jpg");

        return "controllerblock";
    }

}
