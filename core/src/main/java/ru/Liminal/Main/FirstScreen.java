package ru.Liminal.Main;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.ScreenUtils;


public class FirstScreen implements Screen {
    Main game;
    TiledMap map;
    EntityFaсtory entityFaсtory;
    public FirstScreen(Main game){
        this.game = game;
    }

    @Override
    public void show() {
        entityFaсtory = new EntityFaсtory(game.engine,game.assetManager);

        Map map = new Map("map.tmx", 0,0);

//        game.engine.addSystem(new BasicAISystem());
        game.engine.addSystem(new AISystem(map.getCollisionLayer()));
        game.engine.addSystem(new PlayerControllerSystem());
        game.engine.addSystem(new StaminaSystem());


        game.engine.addSystem(new CollisionSystem(game.assetManager.get("map.tmx", TiledMap.class)));
        game.engine.addSystem(new MovementSystem());



        game.engine.addSystem(new CameraFollowSystem(game.camera));


        game.engine.addSystem(new MapRenderSystem(map, game.camera));
        game.engine.addSystem(new RenderSystem(game.spriteBatch, game.camera));
        game.engine.addSystem(new NameDisplaySystem(game.spriteBatch, game.camera, game.assetManager));
        entityFaсtory.createPlayer();
        entityFaсtory.createNPC();



    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        //dd game.camera.update();
        game.engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
