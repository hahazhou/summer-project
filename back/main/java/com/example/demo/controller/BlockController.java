package com.example.demo.controller;

import org.json.JSONObject;
import com.example.demo.enitiy.Video;
import com.example.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/controllerblock")
public class BlockController {
    @Autowired
    private VideoService videoService;

    @RequestMapping("/changeCamera")
    @ResponseBody
    public Object showmap(HttpServletRequest request, HttpServletResponse httpServletResponse){
        try {
            String camera = request.getParameter("camera");
            int camera_id = 0;
            if (camera.equals("camera_1") ) {
                camera_id = 1;
            } else if (camera .equals("camera_2") ) {
                camera_id = 2;
            } else if (camera.equals("camera_3") ) {
                camera_id = 3;
            }
            Video video = videoService.getVideoByCamera(camera_id);
            JSONObject data = new JSONObject();
            data.put("msg","切换成功");
            data.put("camera",camera);
            data.put("video",video.getVideo_path());
            return data.toString();
        }catch (NullPointerException e){
            return new JSONObject().put("msg","摄像头参数错误").toString();
        }
    }

}
