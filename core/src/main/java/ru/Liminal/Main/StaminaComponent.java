package ru.Liminal.Main;

import com.badlogic.ashley.core.Component;

public class StaminaComponent implements Component {
    float current;
    float speed = 100;
    boolean full = false;

    public StaminaComponent(){

    }
    public StaminaComponent(float speed){
        this.speed = speed;
    }

}
