package com.example.demo.domain;

import com.example.demo.Dao.UserDao;
import com.example.demo.enitiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int insertUser(User user){
        return jdbcTemplate.update("insert into user(username, password) values(?, ?)",
                user.getUsername(),user.getPassword());
    };

    @Override
    public User getUserByName(String username){
        try {
            List<User> list = jdbcTemplate.query("select * from user where username=?", new Object[]{username}, new BeanPropertyRowMapper(User.class));
            return list.get(0);
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    };

    @Override
    public List<User> getAllUsers(){
        try {
            List<User> list = jdbcTemplate.query("select * from user", new Object[]{}, new BeanPropertyRowMapper(User.class));
            return list;
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    };

    @Override
    public int delete(User user){
        return jdbcTemplate.update("DELETE from user where username = ? ",user.getUsername());
    };

    @Override
    public int update(User user){
        return jdbcTemplate.update("UPDATE user SET  password = ? WHERE username=?",
                user.getPassword(),user.getUsername());
    };



}
