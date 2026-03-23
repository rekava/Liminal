package ru.Liminal.Main;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

public class MapComponent implements Component {

    TiledMap map;
    Vector2 startPos = new Vector2(0,0);


    MapComponent(TiledMap map, int x,int y){
        this.map = map;
        this.startPos.set(x,y);
    }


}
