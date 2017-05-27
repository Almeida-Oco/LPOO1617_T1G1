package Controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;

import View.Play;

/**
 * Created by asus on 04/05/2017.
 */

public class Map {
    private TiledMap map;

    public Map(){
        this.map = (new TmxMapLoader()).load(Gdx.files.internal("maps/DKMap.tmx").path());
    }

    public TiledMap getMap(){
        return this.map;
    }

    public void dispose(){
        this.map.dispose();
    }
}
