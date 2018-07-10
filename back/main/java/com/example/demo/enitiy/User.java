package com.example.demo.enitiy;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User implements java.io.Serializable{
    private static final long serialVersionUID = 2120869894112984147L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private int user_id;

    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="role")
    private boolean role;

    public User(){ }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public boolean getRole(){
        return role;
    }

}
