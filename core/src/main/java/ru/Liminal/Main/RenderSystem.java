package ru.Liminal.Main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class RenderSystem extends IteratingSystem {
    SpriteBatch spriteBatch;
    OrthographicCamera camera;
    ComponentMapper<TextureComponent> tc = ComponentMapper.getFor(TextureComponent.class);
    ComponentMapper<PositionComponent> pc = ComponentMapper.getFor(PositionComponent.class);

    final int tileSize = 32;

    public RenderSystem(SpriteBatch spriteBatch, OrthographicCamera camera){
        super(Family.all(TextureComponent.class, PositionComponent.class).get());
        this.spriteBatch = spriteBatch;
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime){
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        super.update(deltaTime);
        spriteBatch.end();
    }
    @Override
    protected void processEntity(Entity entity, float v) {
        TextureComponent textureComponent = tc.get(entity);
        PositionComponent positionComponent = pc.get(entity);

        spriteBatch.draw(
            textureComponent.textureRegion, // 1. Регион
            (positionComponent.renderPosition.x * tileSize) , // 2. X (с учетом origin 100/2)
            (positionComponent.renderPosition.y * tileSize) , // 3. Y (с учетом origin 100/2)
            16, 32,   // 4, 5. OriginX, OriginY (центр вращения)
            32, 64, // 6, 7. Ширина и Высота
            1f, 1f,   // 8, 9. Масштаб (ScaleX, ScaleY)
            0         // 10. Поворот (Rotation)
        );

    }
}
