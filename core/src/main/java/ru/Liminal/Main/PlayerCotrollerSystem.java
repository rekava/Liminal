package ru.Liminal.Main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.security.Key;

class PlayerControllerSystem extends IteratingSystem {
    ComponentMapper<PlayerComponent> plc = ComponentMapper.getFor(PlayerComponent.class);
    ComponentMapper<PositionComponent> pc = ComponentMapper.getFor(PositionComponent.class);
    TiledMap map;
    public PlayerControllerSystem(TiledMap map){
        super(Family.all(PlayerComponent.class,PositionComponent.class).get());
        this.map = map;


    }
    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
    }
    @Override
    protected void processEntity(Entity entity, float v) {
        PositionComponent pos = pc.get(entity);


        float nextX = pos.position.x;
        float nextY = pos.position.y;


        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) nextY += 1;
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) nextY -= 1;
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) nextX -= 1;
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) nextX += 1;


        if (!isCellBlocked(nextX, nextY)) {
            pos.position.x = nextX;
            pos.position.y = nextY;
        }
    }

    public boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("walls");
        if (layer == null) return false;


        int cellX = (int) x;
        int cellY = (int) y;

        TiledMapTileLayer.Cell cell = layer.getCell(cellX, cellY);

        return cell != null &&
            cell.getTile() != null &&
            cell.getTile().getProperties().containsKey("solid");
    }

}
