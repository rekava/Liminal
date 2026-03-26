package ru.Liminal.Main;

import com.badlogic.ashley.core.Component;

public class NameComponent implements Component {
    String name = "none";
    NameComponent(String name){
        this.name = name;
    }
}
