package com.test.summerproject.enitiy;

import javax.persistence.*;

@Entity
@Table(name = "mapphoto")
public class Mapphoto implements java.io.Serializable {
    private static final long serialVersionUID = 2120869894112984147L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "map_id")
    private int map_id;

    @Column(name = "username")
    private String username;
    @Column(name = "map_name")
    private String map_name;
    @Column(name = "map_path")
    private String map_path;
    @Column(name = "height")
    private int height;
    @Column(name = "width")
    private int width;

    Mapphoto() {
    }

    public void setMap_id(int map_id) {
        this.map_id = map_id;
    }

    public int getMap_id() {
        return map_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
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

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }
}
