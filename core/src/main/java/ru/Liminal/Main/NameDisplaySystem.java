package ru.Liminal.Main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NameDisplaySystem extends IteratingSystem {
    // Mappers are the fastest way to get components in Ashley
    private ComponentMapper<NameComponent> nc = ComponentMapper.getFor(NameComponent.class);
    private ComponentMapper<PositionComponent> pc = ComponentMapper.getFor(PositionComponent.class);

    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();

    public NameDisplaySystem(SpriteBatch spriteBatch, OrthographicCamera camera, AssetManager assetManager){
        // We only care about entities that have BOTH a name and a position
        super(Family.all(NameComponent.class, PositionComponent.class).get());
        this.spriteBatch = spriteBatch;
        this.camera = camera;

        // Since we loaded "consolas.ttf" as a BitmapFont.class in Main,
        // we just grab it here. No need for a generator anymore!
        this.font = assetManager.get("consolas.ttf", BitmapFont.class);

    }

    @Override
    public void update(float dt) {
        // 1. Обновляем проекцию камеры (если камера двигалась)
        spriteBatch.setProjectionMatrix(camera.combined);

        // 2. Открываем батч ОДИН раз перед циклом
        spriteBatch.begin();

        // 3. super.update(dt) прогонит все сущности через processEntity
        super.update(dt);

        // 4. Закрываем батч после отрисовки ВСЕХ имен
        spriteBatch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        NameComponent name = nc.get(entity);
        PositionComponent pos = pc.get(entity);

        layout.setText(font,name.name);

        float x = (pos.position.x * 32) + (16 - layout.width / 2);

        // Позиция Y: над головой (64 пикселя) + небольшой зазор
        float y = (pos.position.y * 32) + 70;

        font.draw(spriteBatch, name.name, x, y);
    }
}
