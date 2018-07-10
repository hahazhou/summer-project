package com.example.demo.enitiy;

import javax.persistence.*;

@Entity
@Table(name="map")
public class Mapphoto implements java.io.Serializable{
    private static final long serialVersionUID = 2120869894112984147L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="map_id")
    private int map_id;

    @Column(name="user_id")
    private int user_id;
    @Column(name="map_name")
    private String map_name;
    @Column(name="map_path")
    private String map_path;
    @Column(name="h")
    private int h;
    @Column(name="w")
    private int w;

    Mapphoto(){}

    public void setMap_id(int map_id) {
        this.map_id = map_id;
    }

    public int getMap_id() {
        return map_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setMap_name(String map_name) {
        this.map_name = map_name;
    }

    public String getMap_name() {
        return map_name;
    }

    public void setMap_path(String map_path) {
        this.map_path = map_path;
    }

    public String getMap_path() {
        return map_path;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getH() {
        return h;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getW() {
        return w;
    }
}
