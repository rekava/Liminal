package ru.Liminal.Main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.systems.IteratingSystem;

import java.awt.*;

public class MovementSystem extends IteratingSystem {

    ComponentMapper<PositionComponent> pc = ComponentMapper.getFor(PositionComponent.class);
    ComponentMapper<MoveIntent> mc = ComponentMapper.getFor(MoveIntent.class);

    MovementSystem() {
        super(Family.all(PositionComponent.class, MoveIntent.class).get());

    }

    @Override
    protected void processEntity(Entity entity, float v) {
        PositionComponent positionComponent = pc.get(entity);
        MoveIntent moveIntent = mc.get(entity);


            positionComponent.position.x += moveIntent.movX;
            positionComponent.position.y += moveIntent.movY;
            moveIntent.done = true;
            moveIntent.movX = 0;
            moveIntent.movY = 0;


    }
}
