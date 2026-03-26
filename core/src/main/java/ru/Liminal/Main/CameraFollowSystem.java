package ru.Liminal.Main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class CameraFollowSystem extends IteratingSystem {
    private OrthographicCamera camera;
    private ComponentMapper<PositionComponent> pc = ComponentMapper.getFor(PositionComponent.class);

    // Настрой под себя: 1.0 - очень медленно, 20.0 - почти мгновенно
    private final float LERP_SPEED = 8f;
    private final int tileSize = 32;

    public CameraFollowSystem(OrthographicCamera camera){
        // Следим только за сущностью с PlayerComponent
        super(Family.all(PlayerComponent.class, PositionComponent.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent pos = pc.get(entity);

        // Целевые координаты в пикселях (куда камера ДОЛЖНА прийти)
        float targetX = pos.position.x * tileSize;
        float targetY = pos.position.y * tileSize;

        // Линейная интерполяция позиции камеры
        // Это убирает дерготню, так как камера сама сглаживает путь
        camera.position.x = MathUtils.lerp(camera.position.x, targetX, LERP_SPEED * deltaTime);
        camera.position.y = MathUtils.lerp(camera.position.y, targetY, LERP_SPEED * deltaTime);

        camera.update();
    }
}
