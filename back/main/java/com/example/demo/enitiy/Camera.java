package com.example.demo.enitiy;
import javax.persistence.*;

@Entity
@Table(name="camera")
public class Camera implements java.io.Serializable{
    private static final long serialVersionUID = 2120869894112984147L;
    @Id
    @Column(name="camera_id")
    private int camera_id;

    @Column(name="map_id")
    private int map_id;
    @Column(name="x")
    private int x;
    @Column(name="y")
    private int y;
    @Column(name="redius")
    private int redius;

    @Column(name="direction")
    private float direction;

    @Column(name="angle")
    private float angle;

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

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setRedius(int redius) { this.redius = redius; }

    public int getRedius() { return redius; }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getDirection() {
        return direction;
    }
}
