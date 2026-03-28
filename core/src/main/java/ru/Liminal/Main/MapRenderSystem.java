package ru.Liminal.Main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapRenderSystem extends EntitySystem {


    OrthographicCamera camera;
    OrthogonalTiledMapRenderer mapRenderer;

    public MapRenderSystem(Map map ,OrthographicCamera camera){
        this.camera = camera;
        this.mapRenderer = new OrthogonalTiledMapRenderer(map.map, 1);
    }

    @Override
    public void update(float deltaTime){
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
}


