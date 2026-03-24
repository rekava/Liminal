package ru.Liminal.Main;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class EntityFaсtory {
    Engine engine;
    AssetManager assetManager;

    EntityFaсtory(Engine engine, AssetManager assetManager){
        this.engine = engine;
        this.assetManager = assetManager;
    }

    public void createPlayer(){
        Entity player = engine.createEntity();
        player.add(new PositionComponent(1,3));
        player.add(new TextureComponent(assetManager.get("player.png", Texture.class)));
        player.add(new StaminaComponent());
        player.add(new ColliderComponent());
        player.add(new MoveIntent());
        player.add(new PlayerComponent());

        engine.addEntity(player);
    }

    public void createNPC(){
        Entity npc = engine.createEntity();
        npc.add(new PositionComponent(10,5));
        npc.add(new TextureComponent(assetManager.get("tile.png", Texture.class)));
        npc.add(new AITimerComponent());
        npc.add(new NPCComponent());
        npc.add(new ColliderComponent());
        npc.add(new MoveIntent());
        engine.addEntity(npc);

    }

}
