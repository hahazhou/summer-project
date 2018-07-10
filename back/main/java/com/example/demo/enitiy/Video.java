package com.example.demo.enitiy;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Video implements java.io.Serializable{
    private static final long serialVersionUID = 2120869894112984147L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int video_id;

    @Column
    private int camera_id;
    @Column
    private String video_name;
    @Column
    private String video_path;
    @Column
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
