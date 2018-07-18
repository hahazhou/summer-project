package com.example.demo.domain;

import com.example.demo.Dao.VideoDao;
import com.example.demo.enitiy.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class VideoDaoImpl implements VideoDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String getPathById(int video_id){
        try {
            List<Video> list = jdbcTemplate.query("select * from video where video_id=?", new Object[]{video_id}, new BeanPropertyRowMapper(Video.class));
            return list.get(0).getVideo_path();
        } catch (IndexOutOfBoundsException e){
            return "";
        }
    }

    @Override
    public Video getVideoByCamera(int camera_id){

        try {
            List<Video> list = jdbcTemplate.query("select * from video where camera_id=?", new Object[]{camera_id}, new BeanPropertyRowMapper(Video.class));
            return list.get(0);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public Video getVideoByTime(Date time, int camera_id,int hour){
        try{

            List<Video> list = jdbcTemplate.query("select * from video where time=? and camera_id=? and hour=?",
                    new Object[]{time,camera_id,hour}, new BeanPropertyRowMapper(Video.class));
            return list.get(0);
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }
}
