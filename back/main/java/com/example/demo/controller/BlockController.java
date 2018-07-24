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
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/controllerblock")
public class BlockController {
    @Autowired
    private VideoService videoService;

    /*
    摄像头切换辅助函数
     */
    private int selectCamera(String camera){
        if (camera.equals("camera_0") ) {
            return 0;
        }
        if (camera .equals("camera_1") ) {
            return 1;
        }
        if (camera.equals("camera_2") ) {
            return 2;
        }
        return 5438;
    }

    /*
    摄像头切换函数
     */
    @RequestMapping("/changeCamera")
    @ResponseBody
    public Object showmap(HttpServletRequest request, HttpServletResponse httpServletResponse){
        try {
            String camera = request.getParameter("camera");
            int camera_id =selectCamera(camera);
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


    /*
    调用历史视频函数
     */
    @RequestMapping("/sortVideoByTime")
    @ResponseBody
    public Object sortVideoByTime(HttpServletRequest request,HttpServletResponse response){
        try{
            JSONObject data =new JSONObject();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String time=request.getParameter("time");
            Date date=dateFormat.parse("2018-07-17");
            int hour=Integer.parseInt(request.getParameter("hour"));
            String camera=request.getParameter("camera");
            int camera_id =selectCamera(camera);
            Video video=videoService.getVideoByTime(date,camera_id,hour);
            String srcpath="C:\\Users\\asus\\scanner\\src\\pictrue";//前端src文件夹的绝对路径
            ArrayList<String> filelist=new ArrayList<String>();

            /*
            *视频播放+截屏操作
            * */

            File src=new File(srcpath);
            File[] files=src.listFiles();
            for(File file:files){
                String filepath = file.getPath();
                filelist.add(filepath);
            }
            data.put("filelist",filelist);
            ArrayList<String> list=new ArrayList<String>();
            list.add("person0");
            list.add("person1");
            list.add("person2");
            data.put("personlist",list);
            data.put("msg","SUCCESS");
            data.put("imgname","person.jpg");
            return data.toString();

        }catch (NullPointerException e){
            return new JSONObject().put("msg","NullPointerException").toString();
        }catch (ParseException e){
            return new JSONObject().put("msg","ParseException").toString();
        }
    }

    /*
    * 调用实时视频后端函数
    * */
    @RequestMapping("/showVideo")
    @ResponseBody
    public Object showVideo(HttpServletRequest request,HttpServletResponse response){
       try {
           String camera=request.getParameter("camera");
           int camera_id=selectCamera(camera);
           Video video = videoService.getVideoByCamera(camera_id);
           JSONObject data = new JSONObject();
           /*
           请在这里添加你的代码，王儿子
            */
           return data.toString();
       } catch (NullPointerException e){
            return new JSONObject().put("msg","摄像头参数错误").toString();
       }
    }

    @RequestMapping("/selectPerson")
    @ResponseBody
    public Object selectPerson(HttpServletRequest request,HttpServletResponse response){
        String person = request.getParameter("person");

        /*
        选定person后的操作
        */

        JSONObject data = new JSONObject();
        data.put("msg","success");
        return data.toString();
    }
}
