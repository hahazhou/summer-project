package com.example.demo.Dao;

import com.example.demo.enitiy.User;
import java.util.List;

public interface UserDao {
    int insertUser(User user);
    User getUserByName(String name);
    List<User> getAllUsers();
    int delete(User user);
    int update(User user);

}
