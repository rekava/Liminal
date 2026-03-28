package ru.Liminal.Main;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class AIComponent implements Component {
    public enum State {
        IDLE,
        RANDOM,
        CHASE
    }

    public State state = State.IDLE;
    public State previousState = State.IDLE;


    public float randomTimer = 0;
    public float randomInterval = 2f;
    public Vector2 randomTarget = new Vector2();


    public Entity targetEntity;
    public List<Vector2> path;
    public int currentPathIndex = 0;
    public float recalcPathTimer = 0;
    public float recalcPathInterval = 0.1f;


    public float attackRange = 1.5f;
    public float viewRange = 15f;
    public float waitTimer = 0;
    public float waitDuration = 1f;
}
