package ru.Liminal.Main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraFollowSystem extends IteratingSystem {
    OrthographicCamera camera;
    ComponentMapper<PlayerComponent> plc = ComponentMapper.getFor(PlayerComponent.class);
    ComponentMapper<PositionComponent> pc = ComponentMapper.getFor(PositionComponent.class);

    public CameraFollowSystem(OrthographicCamera camera){
        super(Family.all(PlayerComponent.class, PositionComponent.class).get());
        this.camera = camera;
    }
    @Override
    protected void processEntity(Entity entity, float v) {
        PositionComponent positionComponent = pc.get(entity);
        float targetX = positionComponent.renderPosition.x * 32;
        float targetY = positionComponent.renderPosition.y * 32;

        camera.position.set(targetX, targetY, 0);
        camera.update();
    }
}
