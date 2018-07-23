package com.test.summerproject.controller;


import org.json.JSONObject;
import com.test.summerproject.enitiy.Video;
import com.test.summerproject.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.test.summerproject.function.*;

@Controller
@RequestMapping("/controllerblock")
public class BlockController {
    @Autowired
    private VideoService videoService;

    private int selectCamera(String camera) {
        if (camera.equals("camera_0")) {
            return 0;
        }
        if (camera.equals("camera_1")) {
            return 1;
        }
        if (camera.equals("camera_2")) {
            return 2;
        }
        return -1;
    }

    @RequestMapping("/changeCamera")
    @ResponseBody
    public Object showmap(HttpServletRequest request, HttpServletResponse response) {
        System.load("C:\\OpenCV\\opencv\\build\\java\\x64\\opencv_java320.dll");
        System.setProperty("java.awt.headless", "false");
        JSONObject data = new JSONObject();
        String camera = request.getParameter("camera");
        int camera_id = selectCamera(camera);
        try {
            Video video = videoService.getVideoByCamera(camera_id);

            data.put("msg", "切换成功");
            data.put("camera", camera);
            data.put("video", video.getVideo_path());
            return data.toString();
        } catch (Exception e) {
            data.put("msg", "摄像头错误");
            data.put("camera", camera);
            return data.toString();
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
            Date date=dateFormat.parse(time);
            int hour=Integer.parseInt(request.getParameter("hour"));
            String camera=request.getParameter("camera");
            int camera_id =selectCamera(camera);
            Video video=videoService.getVideoByTime(date,camera_id,hour);
            String srcpath="G\\test\\summer-project\\src\\main\\resources\\temp_pic";//前端src文件夹的绝对路径
            ArrayList<String> filelist=new ArrayList<String>();
            String filename=video.getVideo_path();

            /*
             *视频播放+截屏操作
             * */

            PlayVideo.run(filename);
            SSD_dectect.runSSD("G:/SummerProject/snapshot/snapshot","G:/SummerProject/snapshot",null);
            MovePhoto.movePhoto(MovePhoto.moveType.FOLDER,"G:\\SummerProject\\snapshot\\image_tested",srcpath,null);


            File src=new File(srcpath);
            File[] files=src.listFiles();
            for(File file:files){
                String filepath = file.getPath();
                filelist.add(filepath);
            }
            data.put("filelist",filelist);


            data.put("msg","SUCCESS");
            return data.toString();

        }catch (NullPointerException e){
            e.printStackTrace();
            return new JSONObject().put("msg","NullPointerException").toString();
        }catch (ParseException e){
            e.printStackTrace();
            return new JSONObject().put("msg","ParseException").toString();
        }catch (IOException e){
            e.printStackTrace();
            return new JSONObject().put("msg","IOException").toString();
        }
    }


    //打开实时摄像头
    @RequestMapping("/showVideo")
    @ResponseBody
    public Object showVideo(HttpServletRequest request, HttpServletResponse response) {
        try {
            String camera = request.getParameter("camera");
            int camera_id = selectCamera(camera);
            Video video = videoService.getVideoByCamera(camera_id);

            if (Camera.opencamera(camera_id) == false) {
                return new JSONObject().put("msg", "摄像头无法打开").toString();
            }
            return new JSONObject().put("msg", "摄像头成功打开").toString();
        } catch (NullPointerException e) {
            return new JSONObject().put("msg", "摄像头参数错误").toString();
        } catch (UnsatisfiedLinkError e) {
            return new JSONObject().put("msg", "摄像头无法打开").toString();
        } catch (Exception e) {
            return new JSONObject().put("msg", "error").toString();
        }
    }
}
