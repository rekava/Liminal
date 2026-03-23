package ru.Liminal.Main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapRenderSystem extends IteratingSystem {


    OrthographicCamera camera;
    OrthogonalTiledMapRenderer mapRenderer;
    ComponentMapper<MapComponent> mp = ComponentMapper.getFor(MapComponent.class);

    public MapRenderSystem(OrthographicCamera camera){
        super(Family.all(MapComponent.class).get());

        this.camera = camera;
    }
    public void update(float deltaTime){
        //spriteBatch.setProjectionMatrix(camera.combined);

        super.update(deltaTime);

    }
    @Override
    protected void processEntity(Entity entity, float v) {
        MapComponent mapComponent = mp.get(entity);

        mapRenderer = new OrthogonalTiledMapRenderer(mapComponent.map, 1);
        mapRenderer.setView(camera);
        mapRenderer.render();
    }
}
