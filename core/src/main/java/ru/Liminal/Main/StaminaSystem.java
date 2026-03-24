package ru.Liminal.Main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class StaminaSystem extends IteratingSystem {

    ComponentMapper<StaminaComponent> sc = ComponentMapper.getFor(StaminaComponent.class);

    StaminaSystem(){
        super(Family.all(StaminaComponent.class).get());
    }

    public void update(float dt){
        super.update(dt);
    }

    @Override
    protected void processEntity(Entity entity, float v) {

        StaminaComponent staminaComponent = sc.get(entity);


        if(!staminaComponent.full) {
            staminaComponent.current += staminaComponent.speed * 20 * v;

        }
        if(staminaComponent.current > 100 && !staminaComponent.full){
            staminaComponent.current -= 100;
            staminaComponent.full = true;
        }






    }
}
