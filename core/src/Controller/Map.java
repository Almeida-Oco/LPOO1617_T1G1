package Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;

import View.Play;

/**
 * Created by asus on 04/05/2017.
 */

public class Map {
    private TiledMap map;
    private TiledMapTileLayer collision_layer;

    public Map(){
        this.map = (new TmxMapLoader()).load(Gdx.files.internal("maps/DKMap.tmx").path());
        this.collision_layer = (TiledMapTileLayer)this.map.getLayers().get("Floor");
    }

    public TiledMap getMap(){
        return this.map;
    }

    public void dispose(){
        this.map.dispose();
    }

    public boolean collidesRight(Pair<Integer,Integer> pos, Pair<Integer,Integer> rep_size) {
        for(float step = 0; step < rep_size.getSecond(); step += this.collision_layer.getTileHeight() / 2)
            if(isCellBlocked( (float)pos.getFirst() + rep_size.getFirst(), (float)pos.getSecond() + step))
                return true;
        return false;
    }

    public boolean collidesLeft(Pair<Integer,Integer> pos, Pair<Integer,Integer> rep_size) {
        for(float step = 0; step < rep_size.getSecond(); step += this.collision_layer.getTileHeight() / 2)
            if(isCellBlocked( (float)pos.getFirst() , (float)pos.getSecond() + step))
                return true;
        return false;
    }

    public boolean collidesTop(Pair<Integer,Integer> pos, Pair<Integer,Integer> rep_size) {
        for(float step = 0; step < rep_size.getFirst(); step += this.collision_layer.getTileWidth() / 2)
            if(isCellBlocked( (float)pos.getFirst() + step , (float)pos.getSecond() + rep_size.getSecond()) )
                return true;
        return false;

    }

    public boolean collidesBottom(Pair<Integer,Integer> pos, Pair<Integer,Integer> rep_size) {
        for(float step = 0; step < rep_size.getFirst(); step += this.collision_layer.getTileWidth() / 2)
            if( isCellBlocked( (float)pos.getFirst() + step, (float)pos.getSecond() ) )
                return true;
        return false;
    }

    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = this.collision_layer.getCell( (int) (x / this.collision_layer.getTileWidth()),
                                                                    (int) (y / this.collision_layer.getTileHeight()));

        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");

    }

}
