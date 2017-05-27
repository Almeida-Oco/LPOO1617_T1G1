package Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Map {
    private TiledMap map;
    private TiledMapTileLayer collision_layer;
    private float scale = 1f;

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
        for(float step = 0; step < rep_size.getSecond(); step += this.collision_layer.getTileHeight() / 2){
            float x = (float)pos.getFirst()/this.scale + rep_size.getFirst() , y = (pos.getSecond()-this.collision_layer.getTileHeight() + step);
            if(isCellBlocked( x , y ) )
                return true;
        }

        return false;
    }

    public boolean collidesLeft(Pair<Integer,Integer> pos, Pair<Integer,Integer> rep_size) {
        for(float step = 0; step < rep_size.getSecond(); step += this.collision_layer.getTileHeight() / 2){
            float x  = (float)pos.getFirst()/this.scale, y = (pos.getSecond()-this.collision_layer.getTileHeight() + step);
            if ( isCellBlocked( x , y ) )
                return true;
        }
        return false;
    }

    //TODO why do we need that -1 5 lines below here
    //TODO Near the edge Mario falls before he is supposed to
    public int collidesBottom(Pair<Integer,Integer> pos, Pair<Integer,Integer> rep_size) {
        for(float step = 0; step < rep_size.getFirst(); step += this.collision_layer.getTileWidth()/2 ){
            float x = (float)pos.getFirst()/this.scale + step, y = (pos.getSecond()-rep_size.getSecond())/this.scale;
            if( isCellBlocked(x,y) )
                return (int)(this.getTopTile(x,y) * this.collision_layer.getTileHeight() * this.scale + rep_size.getSecond()-1);
        }

        return -1;
    }

    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = this.collision_layer.getCell( (int) (x / this.collision_layer.getTileWidth()),
                                                                    (int) (y / this.collision_layer.getTileHeight()));

        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");

    }

    public boolean nearLadder(Pair<Integer,Integer> pos, Pair<Integer,Integer> rep_size){
        int     x = (int)( (pos.getFirst()+rep_size.getFirst()/2)/(this.scale*this.collision_layer.getTileWidth())),
                y = (int)( (pos.getSecond()-rep_size.getSecond())/(this.scale*this.collision_layer.getTileHeight()));

        TiledMapTileLayer.Cell tile = ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell( x , y );

        return (tile != null && tile.getTile() != null);
    }

    public int getMapTileWidth() {
        return (int)this.collision_layer.getTileWidth();
    }

    public int getMapTileHeight(){
        return (int)this.collision_layer.getTileHeight();
    }

    public int getMapHeight(){
        return (int)(this.collision_layer.getHeight()*this.collision_layer.getTileHeight());
    }

    public int getMapWidth(){
        return (int)(this.collision_layer.getWidth()*this.collision_layer.getTileWidth());
    }

    public void setScale( float scale ){
        this.scale = scale;
    }

    //TODO why the fuck do we need layer_y - 6 ???
    private int getTopTile(float x , float y){
        int layer_x = (int)(x/this.collision_layer.getTileWidth()), layer_y = (int)(y/this.collision_layer.getTileHeight());
        while ( this.collision_layer.getCell(layer_x,layer_y) != null)
            layer_y++;

        return layer_y-6;
    }



}
