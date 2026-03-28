package ru.Liminal.Main;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class AISystem extends IteratingSystem {

    private final ComponentMapper<AIComponent> aiMapper = ComponentMapper.getFor(AIComponent.class);
    private final ComponentMapper<PositionComponent> posMapper = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<MoveIntent> moveMapper = ComponentMapper.getFor(MoveIntent.class);
    private final ComponentMapper<StaminaComponent> staminaMapper = ComponentMapper.getFor(StaminaComponent.class);

    private ImmutableArray<Entity> players;
    private final AStar pathfinder;
    private final Random random = new Random();

    public AISystem(TiledMapTileLayer collisionLayer) {
        super(Family.all(AIComponent.class, PositionComponent.class, MoveIntent.class, StaminaComponent.class).get());
        this.pathfinder = new AStar(collisionLayer);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AIComponent ai = aiMapper.get(entity);
        PositionComponent pos = posMapper.get(entity);
        MoveIntent move = moveMapper.get(entity);
        StaminaComponent stamina = staminaMapper.get(entity);


        if (!stamina.full) {
            return;
        }


        if (ai.targetEntity == null || !players.contains(ai.targetEntity, true)) {
            ai.targetEntity = findNearestPlayer(pos.position);
        }


        updateState(ai, pos, deltaTime);


        switch (ai.state) {
            case IDLE:
                updateIdle(ai, deltaTime);
                break;
            case RANDOM:
                updateRandom(ai, move, stamina);
                break;
            case CHASE:
                updateChase(ai, pos, move, stamina, deltaTime);
                break;
        }
    }

    private void updateState(AIComponent ai, PositionComponent pos, float deltaTime) {
        if (ai.targetEntity == null) {
            if (ai.state == AIComponent.State.CHASE) ai.state = AIComponent.State.IDLE;
            return;
        }

        PositionComponent targetPos = posMapper.get(ai.targetEntity);
        if (targetPos == null) return;

        float distance = pos.position.dst(targetPos.position);

        if (distance <= ai.viewRange) {
            if (ai.state != AIComponent.State.CHASE) {
                ai.state = AIComponent.State.CHASE;
                ai.recalcPathTimer = ai.recalcPathInterval;
            }
        } else if (ai.state == AIComponent.State.CHASE) {
            ai.state = AIComponent.State.RANDOM;
            ai.randomTimer = 0;
        }
    }

    private void updateIdle(AIComponent ai, float deltaTime) {
        ai.waitTimer += deltaTime;
        if (ai.waitTimer >= ai.waitDuration) {
            ai.state = AIComponent.State.RANDOM;
            ai.waitTimer = 0;
        }
    }

    private void updateRandom(AIComponent ai, MoveIntent move, StaminaComponent stamina) {

        int dx = random.nextInt(3) - 1; // -1, 0, 1
        int dy = random.nextInt(3) - 1;

        if (dx != 0 || dy != 0) {
            move.movX = dx;
            move.movY = dy;
            move.done = false;
            stamina.full = false;
        }
    }

    private void updateChase(AIComponent ai, PositionComponent pos, MoveIntent move, StaminaComponent stamina, float deltaTime) {
        PositionComponent targetPos = posMapper.get(ai.targetEntity);
        if (targetPos == null) return;

        ai.recalcPathTimer += deltaTime;


        if (ai.recalcPathTimer >= ai.recalcPathInterval || ai.path == null || ai.path.isEmpty()) {
            ai.recalcPathTimer = 0;

            Vector2 start = new Vector2((int)pos.position.x, (int)pos.position.y);
            Vector2 goal = new Vector2((int)targetPos.position.x, (int)targetPos.position.y);
            ai.path = pathfinder.findPath(start, goal);
            ai.currentPathIndex = 0;
        }

        if (ai.path != null && ai.currentPathIndex < ai.path.size()) {
            Vector2 nextStep = ai.path.get(ai.currentPathIndex);


            int dx = (int)nextStep.x - (int)pos.position.x;
            int dy = (int)nextStep.y - (int)pos.position.y;


            if (dx == 0 && dy == 0) {
                ai.currentPathIndex++;

                if (ai.currentPathIndex < ai.path.size()) {
                    updateChase(ai, pos, move, stamina, deltaTime);
                }
            } else {

                move.movX = Integer.compare(dx, 0);
                move.movY = Integer.compare(dy, 0);
                move.done = false;
                stamina.full = false;
            }
        }
    }

    private Entity findNearestPlayer(Vector2 myPos) {
        if (players.size() == 0) return null;
        Entity closest = null;
        float minDst = Float.MAX_VALUE;

        for (Entity player : players) {
            PositionComponent pPos = posMapper.get(player);
            if (pPos != null) {
                float dst = myPos.dst2(pPos.position);
                if (dst < minDst) {
                    minDst = dst;
                    closest = player;
                }
            }
        }
        return closest;
    }
}
