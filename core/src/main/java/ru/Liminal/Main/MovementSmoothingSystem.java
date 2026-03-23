package ru.Liminal.Main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;

import java.util.Map;

public class MovementSmoothingSystem extends IteratingSystem {

    ComponentMapper<PositionComponent> pc = ComponentMapper.getFor(PositionComponent.class);
    float speed = 10;

    public MovementSmoothingSystem(){
        super(Family.all(PositionComponent.class).get());

    }
    @Override
    protected void processEntity(Entity entity, float v) {
        PositionComponent positionComponent = pc.get(entity);

        positionComponent.renderPosition.x = MathUtils.lerp(positionComponent.renderPosition.x, positionComponent.position.x, speed * v);
        positionComponent.renderPosition.y = MathUtils.lerp(positionComponent.renderPosition.y, positionComponent.position.y, speed * v);

        if(positionComponent.renderPosition.dst(positionComponent.position) < 0.01f){
            positionComponent.renderPosition.set(positionComponent.position);
        }
    }
}
