package com.example.demo.enitiy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="video")
public class Video implements java.io.Serializable{
    private static final long serialVersionUID = 2120869894112984147L;
    @Id
    @Column(name="video_id")
    private int video_id;

    @Column(name="camera_id")
    private int camera_id;
    @Column(name="video_name")
    private String video_name;
    @Column(name="video_path")
    private String video_path;
    @Column(name="time")
    private Date time;

    Video(){}

    public void setCamera_id(int camera_id) {
        this.camera_id = camera_id;
    }

    public int getCamera_id() {
        return camera_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }
}
