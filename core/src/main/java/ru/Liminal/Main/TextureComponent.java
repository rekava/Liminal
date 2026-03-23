package ru.Liminal.Main;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureComponent implements Component {
    TextureRegion textureRegion = null;

    public TextureComponent (Texture texture){
        textureRegion = new TextureRegion(texture);

    }

}
