package com.test.summerproject.Dao;

import com.test.summerproject.enitiy.Mapphoto;

import java.util.List;

public interface MapphotoDao {
    int uploadMap(Mapphoto mapphoto);

    Mapphoto getMapByName(String username, String map_name);

    List<Mapphoto> maplist(String username);
}
