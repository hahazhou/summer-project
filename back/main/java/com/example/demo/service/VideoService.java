package com.example.demo.service;

import com.example.demo.Dao.VideoDao;
import com.example.demo.enitiy.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VideoService {
    @Autowired
    private VideoDao videoDao;

    public String getPathById(int video_id){ return videoDao.getPathById(video_id);}
    public Video getVideoByCamera(int camera_id){return videoDao.getVideoByCamera(camera_id);}
    public Video getVideoByTime(Date time,int camera_id,int hour){return videoDao.getVideoByTime(time,camera_id,hour);}
}
