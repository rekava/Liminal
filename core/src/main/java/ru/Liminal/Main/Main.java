package ru.Liminal.Main;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.awt.image.TileObserver;

public class Main extends Game {
    public AssetManager assetManager;
    SpriteBatch spriteBatch;
    Engine engine;
    OrthographicCamera camera;
    InternalFileHandleResolver resolver = new InternalFileHandleResolver();

    @Override
    public void create() {
        assetManager = new AssetManager();

        assetManager.load("player.png", Texture.class);
        assetManager.load("kohaku.png", Texture.class);
        assetManager.load("kohaku_1.png", Texture.class);

        assetManager.load("tile.png", Texture.class);


        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load("map.tmx",TiledMap.class);


        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        params.fontFileName = "consolas.ttf";
        params.fontParameters.size = 14;
        params.fontParameters.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяぁあいいサスりびゔををジススり";
        assetManager.load("consolas.ttf", BitmapFont.class, params);


        assetManager.finishLoading();

        spriteBatch = new SpriteBatch();
        engine = new Engine();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,1200,800);

        setScreen(new FirstScreen(this));
    }

    @Override
    public void dispose(){
        assetManager.dispose();
        spriteBatch.dispose();
    }
}
