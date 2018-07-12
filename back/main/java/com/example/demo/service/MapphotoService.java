package com.example.demo.service;

import com.example.demo.Dao.MapphotoDao;
import com.example.demo.enitiy.Mapphoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapphotoService {
    @Autowired
    private MapphotoDao mapphotoDao;

    public Mapphoto getMapByName(String username,String map_name){
        return mapphotoDao.getMapByName(username,map_name);
    };

    public List<Mapphoto> mapphotoList(String username){
        return mapphotoDao.maplist(username);
    }
}
