package ru.Liminal.Main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.security.Key;

class PlayerControllerSystem extends IteratingSystem {
    ComponentMapper<PlayerComponent> plc = ComponentMapper.getFor(PlayerComponent.class);
    ComponentMapper<PositionComponent> pc = ComponentMapper.getFor(PositionComponent.class);
    ComponentMapper<StaminaComponent> sc = ComponentMapper.getFor(StaminaComponent.class);
    ComponentMapper<MoveIntent> mc = ComponentMapper.getFor(MoveIntent.class);


    public PlayerControllerSystem(){
        super(Family.all(PlayerComponent.class,PositionComponent.class, MoveIntent.class).get());


    }
    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
    }
    @Override
    protected void processEntity(Entity entity, float v) {
        PositionComponent pos = pc.get(entity);
        StaminaComponent staminaComponent = sc.get(entity);
        MoveIntent moveIntent = mc.get(entity);

        if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.W) && staminaComponent.full) {
                moveIntent.movY += 1;
                moveIntent.done = false;
                staminaComponent.full = false;

            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.S) && staminaComponent.full) {
                moveIntent.movY -= 1;
                moveIntent.done = false;
                staminaComponent.full = false;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.A) && staminaComponent.full) {
                moveIntent.movX -= 1;
                moveIntent.done = false;
                staminaComponent.full = false;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.D) && staminaComponent.full) {
                moveIntent.movX += 1;
                moveIntent.done = false;
                staminaComponent.full = false;
            }
        }
        else {
            if (Gdx.input.isKeyPressed(Input.Keys.W) && staminaComponent.full) {
                moveIntent.movY += 1;
                moveIntent.done = false;
                staminaComponent.full = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) && staminaComponent.full) {
                moveIntent.movY -= 1;
                moveIntent.done = false;
                staminaComponent.full = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && staminaComponent.full) {
                moveIntent.movX -= 1;
                moveIntent.done = false;
                staminaComponent.full = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) && staminaComponent.full) {
                moveIntent.movX += 1;
                moveIntent.done = false;
                staminaComponent.full = false;
            }
        }
    }



}
