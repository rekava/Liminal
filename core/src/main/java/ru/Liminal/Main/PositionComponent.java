package ru.Liminal.Main;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {
    Vector2 position = new Vector2(0,0);

    Vector2 renderPosition = new Vector2(0,0);
    public PositionComponent(int x, int y){
        this.position.x = x;
        this.position.y = y;

        renderPosition.set(position);
    }

}
