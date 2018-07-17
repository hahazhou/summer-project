package com.example.demo.Dao;

import com.example.demo.enitiy.Video;

public interface VideoDao {
    String getPathById(int vide0_id);
    Video getVideoByCamera(int camera_id);
}
