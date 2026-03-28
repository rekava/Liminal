package ru.Liminal.Main;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;


import java.util.Random;

public class BasicAISystem extends IteratingSystem {
    ComponentMapper<PositionComponent> pc = ComponentMapper.getFor(PositionComponent.class);
    ComponentMapper<NPCComponent> nc = ComponentMapper.getFor(NPCComponent.class);
    ComponentMapper<MoveIntent> mc = ComponentMapper.getFor(MoveIntent.class);
    ComponentMapper<StaminaComponent> sc = ComponentMapper.getFor(StaminaComponent.class);

    Random random;
    int randStep;
    int randDir;

    public BasicAISystem(){
        super(Family.all(PositionComponent.class, NPCComponent.class, MoveIntent.class, StaminaComponent.class).get());
        random = new Random();
    }
    @Override
    public void update(float dt){
        super.update(dt);
    }
    @Override
    protected void processEntity(Entity entity, float v) {
        PositionComponent positionComponent = pc.get(entity);
        MoveIntent moveIntent = mc.get(entity);
        StaminaComponent staminaComponent = sc.get(entity);

        if(staminaComponent.full) {
            randStep = random.nextInt(1) + 1;
            randDir = random.nextInt(4) + 1;
            switch (randDir) {
                case 1:

                    moveIntent.movX += 1;
                    staminaComponent.full = false;
                    break;
                case 2:

                    moveIntent.movX -= 1;
                    staminaComponent.full = false;
                    break;
                case 3:

                    moveIntent.movY += 1;
                    staminaComponent.full = false;
                    break;
                case 4:

                    moveIntent.movY -= 1;
                    staminaComponent.full = false;
                    break;

            }
        }




    }



    }
