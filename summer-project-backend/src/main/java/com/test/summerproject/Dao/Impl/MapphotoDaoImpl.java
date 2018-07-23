package com.test.summerproject.Dao.Impl;

import com.test.summerproject.Dao.MapphotoDao;
import com.test.summerproject.enitiy.Mapphoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MapphotoDaoImpl implements MapphotoDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int uploadMap(Mapphoto mapphoto) {
        return jdbcTemplate.update("insert into mapphoto(map_name, map_path, width, height, username) values(?,?,?,?,?)",
                mapphoto.getMap_name(), mapphoto.getMap_path(), mapphoto.getWidth(), mapphoto.getHeight(), mapphoto.getUsername());
    }



    @Override
    public Mapphoto getMapByName(String username, String map_name) {
        try {
            List<Mapphoto> list = jdbcTemplate.query("select * from mapphoto where username=? and map_name=?",
                    new Object[]{username, map_name}, new BeanPropertyRowMapper(Mapphoto.class));
            return list.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }



    @Override
    public List<Mapphoto> maplist(String username) {
        try {
            List<Mapphoto> list = jdbcTemplate.query("select * from mapphoto where username=?", new Object[]{username}, new BeanPropertyRowMapper(Mapphoto.class));
            return list;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }


}
