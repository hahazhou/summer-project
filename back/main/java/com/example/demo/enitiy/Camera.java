package com.example.demo.enitiy;
import javax.persistence.*;

@Entity
public class Camera implements java.io.Serializable{
    private static final long serialVersionUID = 2120869894112984147L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int camera_id;

    @Column
    private int map_id;
    @Column
    private String camera_name;
    @Column
    private int x;
    @Column
    private int y;
    @Column
    private int r;

    Camera(){}


    public void setCamera_id(int camera_id) {
        this.camera_id = camera_id;
    }

    public int getCamera_id() {
        return camera_id;
    }

    public void setMap_id(int map_id) {
        this.map_id = map_id;
    }

    public int getMap_id() {
        return map_id;
    }

    public void setCamera_name(String camera_name) {
        this.camera_name = camera_name;
    }

    public String getCamera_name() {
        return camera_name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getR() {
        return r;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
}
