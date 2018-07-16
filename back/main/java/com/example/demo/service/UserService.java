package com.example.demo.service;

import com.example.demo.enitiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.example.demo.Dao.UserDao;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }


    public int insertUser(User user) {
        try {
            User userDb = getUserByName(user.getUsername());
            if(Objects.isNull(userDb)){
                return userDao.insertUser(user);
            }
            return -1;
        }catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }


    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
}
