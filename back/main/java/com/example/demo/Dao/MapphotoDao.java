package com.example.demo.Dao;

import com.example.demo.enitiy.Mapphoto;

import java.util.List;

public interface MapphotoDao {
    int uploadMap(Mapphoto mapphoto);
    Mapphoto getMapByName(String username,String map_name);
    List<Mapphoto> maplist(String username);
}
