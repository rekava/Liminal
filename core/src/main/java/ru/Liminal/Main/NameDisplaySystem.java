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

    private ComponentMapper<NameComponent> nc = ComponentMapper.getFor(NameComponent.class);
    private ComponentMapper<PositionComponent> pc = ComponentMapper.getFor(PositionComponent.class);

    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();

    private final int TILE_SIZE = 32;
    private final int SPRITE_WIDTH = 32;

    public NameDisplaySystem(SpriteBatch spriteBatch, OrthographicCamera camera, AssetManager assetManager){
        super(Family.all(NameComponent.class, PositionComponent.class).get());
        this.spriteBatch = spriteBatch;
        this.camera = camera;
        this.font = assetManager.get("consolas.ttf", BitmapFont.class);
    }

    @Override
    public void update(float dt) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        super.update(dt);
        spriteBatch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        NameComponent name = nc.get(entity);
        PositionComponent pos = pc.get(entity);

        layout.setText(font, name.name);
        float textWidth = layout.width;


        float spriteX = pos.position.x * TILE_SIZE;
        float spriteY = pos.position.y * TILE_SIZE;


        float x = spriteX + (SPRITE_WIDTH / 2) - (textWidth / 2);
        float y = spriteY + 64;

        font.draw(spriteBatch, name.name, x, y);
    }
}
