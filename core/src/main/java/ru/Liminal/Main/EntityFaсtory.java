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
        player.add(new NameComponent("Рома"));
        player.add(new PlayerComponent());

        engine.addEntity(player);
    }

    public void createNPC(){
        Entity npc = engine.createEntity();
        npc.add(new PositionComponent(15,10));
        npc.add(new TextureComponent(assetManager.get("kohaku_1.png", Texture.class)));
        npc.add(new TimerComponent());
        npc.add(new NPCComponent());
        npc.add(new ColliderComponent());
        npc.add(new MoveIntent());
        npc.add(new NameComponent("Кохаку"));
        npc.add(new StaminaComponent(16));
        npc.add(new AIComponent());
        engine.addEntity(npc);

    }

}
