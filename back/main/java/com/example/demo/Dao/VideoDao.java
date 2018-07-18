package com.example.demo.Dao;

import com.example.demo.enitiy.Video;

import java.util.Date;

public interface VideoDao {
    String getPathById(int vide0_id);
    Video getVideoByCamera(int camera_id);
    Video getVideoByTime(Date time,int camera_id,int hour);
}
