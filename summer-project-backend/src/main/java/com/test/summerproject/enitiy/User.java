package com.test.summerproject.enitiy;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User implements java.io.Serializable {
    private static final long serialVersionUID = 2120869894112984147L;
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    public User() {
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

}
