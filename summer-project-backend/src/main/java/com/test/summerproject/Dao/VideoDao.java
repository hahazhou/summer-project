package com.test.summerproject.Dao;

import com.test.summerproject.enitiy.Video;

import java.util.Date;

public interface VideoDao {
    String getPathById(int vide0_id);

    Video getVideoByCamera(int camera_id);

    Video getVideoByTime(Date time, int camera_id, int hour);
}
