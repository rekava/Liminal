package ru.Liminal.Main;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;


import java.util.Random;

public class BasicAISystem extends IteratingSystem {
    ComponentMapper<PositionComponent> pc = ComponentMapper.getFor(PositionComponent.class);
    ComponentMapper<AITimerComponent> tc = ComponentMapper.getFor(AITimerComponent.class);
    ComponentMapper<NPCComponent> nc = ComponentMapper.getFor(NPCComponent.class);
    ComponentMapper<MoveIntent> mc = ComponentMapper.getFor(MoveIntent.class);

    Random random;
    int randStep;
    int randDir;

    public BasicAISystem(){
        super(Family.all(PositionComponent.class, AITimerComponent.class, NPCComponent.class, MoveIntent.class).get());
        random = new Random();
    }
    @Override
    public void update(float dt){
        super.update(dt);
    }
    @Override
    protected void processEntity(Entity entity, float v) {
        PositionComponent positionComponent = pc.get(entity);
        AITimerComponent aiTimerComponent = tc.get(entity);
        MoveIntent moveIntent = mc.get(entity);




        aiTimerComponent.currentTime += v;
       // System.out.println(aiTimerComponent.currentTime);
        if(aiTimerComponent.currentTime > aiTimerComponent.interval){
            randStep = random.nextInt(1)+1;
            randDir = random.nextInt(4)+1;
            switch (randDir){
                case 1:
                    //positionComponent.position.x += randStep;
                    moveIntent.movX += 1;
                    break;
                case 2:
                    //positionComponent.position.x -= randStep;
                    moveIntent.movX -= 1;
                    break;
                case 3:
                    //positionComponent.position.y += randStep;
                    moveIntent.movY += 1;
                    break;
                case 4:
                    //positionComponent.position.y -= randStep;
                    moveIntent.movY -= 1;
                    break;

            }

            aiTimerComponent.currentTime = 0;


        }



    }
}
