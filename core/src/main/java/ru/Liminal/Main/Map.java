package ru.Liminal.Main;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

public class Map {
    TiledMap map;
    Vector2 startPos;

    Map(String filePatch, int x, int y){
        this.map = new TmxMapLoader().load(filePatch);
        startPos = new Vector2(x,y);
    }

    TiledMapTileLayer getCollisionLayer(){
        return (TiledMapTileLayer) map.getLayers().get("walls");
    }


}
