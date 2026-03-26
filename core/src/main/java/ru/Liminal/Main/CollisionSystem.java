package ru.Liminal.Main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class CollisionSystem extends IteratingSystem {
    ComponentMapper<ColliderComponent> cc = ComponentMapper.getFor(ColliderComponent.class);
    ComponentMapper<PositionComponent> pc = ComponentMapper.getFor(PositionComponent.class);
    ComponentMapper<MoveIntent> mc = ComponentMapper.getFor(MoveIntent.class);

    TiledMap map;
    CollisionSystem(TiledMap map){
        super(Family.all(ColliderComponent.class, PositionComponent.class, MoveIntent.class).get());
        this.map = map;
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        ColliderComponent colliderComponent = cc.get(entity);
        PositionComponent positionComponent = pc.get(entity);
        MoveIntent moveIntent = mc.get(entity);

        if (isCellBlocked(positionComponent.position.x+ moveIntent.movX, positionComponent.position.y + moveIntent.movY)) {
            moveIntent.movX = 0;
            moveIntent.movY = 0;

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
