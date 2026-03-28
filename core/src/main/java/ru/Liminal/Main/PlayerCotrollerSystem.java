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
        StaminaComponent stamina = sc.get(entity);
        MoveIntent move = mc.get(entity);
        // PositionComponent нам тут нужен только если мы проверяем коллизии заранее,
        // но сейчас мы просто задаем НАМЕРЕНИЕ (MoveIntent).

        if (!stamina.full) return;

        // Сбрасываем старые значения намерения перед опросом клавиш
        move.movX = 0;
        move.movY = 0;

        boolean shift = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) move.movY = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) move.movY = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) move.movX = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) move.movX = 1;


        if (move.movX != 0 || move.movY != 0) {


            boolean isStartingMove = Gdx.input.isKeyJustPressed(Input.Keys.W) ||
                Gdx.input.isKeyJustPressed(Input.Keys.S) ||
                Gdx.input.isKeyJustPressed(Input.Keys.A) ||
                Gdx.input.isKeyJustPressed(Input.Keys.D);

            if (shift || isStartingMove) {
                stamina.full = false;
                move.done = false;
            } else {

                move.movX = 0;
                move.movY = 0;
            }
        }
    }



}
