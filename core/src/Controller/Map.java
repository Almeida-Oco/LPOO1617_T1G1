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

    public float getMapTileWidth(){
        return (this.collision_layer.getTileWidth() * this.scale);
    }

    public float getMapTileHeight(){
        return (this.collision_layer.getTileHeight() * this.scale);
    }

    public float getMapScale(){
        return this.scale;
    }

    public void dispose(){
        this.map.dispose();
    }

    /**
     * @brief Checks if the position collides with something below
     * @param pos Position to check
     * @param rep_size Size of the image representing the entity
     * @return The Y coordinate the object should go should it collide, -1 if it does not collide
     */
    public int collidesBottom(Pair<Integer,Integer> pos, Pair<Integer,Integer> rep_size) {
        for(float step = 0; step < rep_size.getFirst(); step += this.collision_layer.getTileWidth() ){
            float   x = (pos.getFirst()-rep_size.getFirst()/2 + step),
                    y = (pos.getSecond()-rep_size.getSecond()/2);
            if( isCellBlocked(x,y) )
                return (int)(this.getTopTile(x,y) * this.collision_layer.getTileHeight() * this.scale + rep_size.getSecond()/2);
        }

        return -1;
    }

    /**
     * @brief Checks if corresponding Map cell is blocked
     * @param x X pixel coordinate to be checked
     * @param y Y pixel coordinate to be checked
     * @return Whether the cell is blocked or not
     * X and Y will be converted to Map coordinates so there is no need to convert it beforehand
     */
    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = this.collision_layer.getCell( this.XConverter(x) , this.YConverter(y) );

        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }

    /**
     * @brief Checks if there is a ladder in the given position
     * @param pos Position to check for ladder
     * @param rep_size Size of the representation of the object wanting to know if it is near ladder
     * @return X coordinate where the player should "teleport" to start climbing ladder, it is the middle of the ladder. -1 if no ladder is near
     */
    public int nearLadder(Pair<Integer,Integer> pos, Pair<Integer,Integer> rep_size){
        int     x = this.XConverter(pos.getFirst()),
                y = this.YConverter((float)pos.getSecond()-rep_size.getSecond()/2.0f + this.getMapTileHeight()/2 );

        TiledMapTileLayer.Cell tile = ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell( x , y );
        if (tile != null && tile.getTile() != null)
            return (int)(x*this.getMapTileWidth() + this.getMapTileWidth()/2);
        else
            return -1;
    }

    public void setScale( float scale ){
        this.scale = scale;
    }

    public TiledMapTileLayer.Cell getStairCell(int x , int y){
        return ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell( this.XConverter(x) , this.YConverter(y) );
    }

    public TiledMapTileLayer.Cell getFloorCell(int x , int y){
        return ((TiledMapTileLayer)this.map.getLayers().get("Floor")).getCell( this.XConverter(x) , this.YConverter(y) );
    }

    //TODO change function name
    public boolean ladderAndCraneBelow(Pair<Integer,Integer> pos, Pair<Integer,Integer> rep_size ){
        TiledMapTileLayer.Cell  floor = this.getFloorCell( pos.getFirst() , pos.getSecond() - rep_size.getSecond()/2 ),
                                stair = this.getStairCell( pos.getFirst() , pos.getSecond() - rep_size.getSecond()/2 );

        return (floor != null && stair != null) || (floor == null && stair != null);
    }

    /**
     * @brief Checks if given number is out of screen height bounds
     * @param pos Number to check, represents Y coordinate of object
     * @param img_height Height of the image representing the object
     * @return If image is out of bounds then the closest coordinate possible to that bound, otherwise param pos
     */
    public int checkOutOfScreenHeight( int pos , int img_height){
        if (pos < 0)
            return 0;
        else if (pos > (Gdx.graphics.getHeight()-img_height) )
            return (Gdx.graphics.getHeight()-img_height);
        else
            return pos;
    }

    /**
     * @brief Checks if given number is out of screen width bounds
     * @param pos Number to check, represents X coordinate of object
     * @param img_width Height of the image representing the object
     * @return If image is out of bounds then the closest coordinate possible to that bound, otherwise param pos
     */
    public int checkOutOfScreenWidth( int pos, int img_width) {
        if (pos < img_width/2)
            return img_width/2;
        else if (pos > Gdx.graphics.getWidth() - img_width)
            return Gdx.graphics.getWidth() - img_width;
        else
            return pos;
    }


    private int getTopTile(float x , float y){
        int layer_x = this.XConverter(x), layer_y = this.YConverter(y);
        while ( this.collision_layer.getCell(layer_x,layer_y) != null)
            layer_y++;

        return layer_y;
    }

    private int XConverter( float x ){
        return (int)(x/(this.scale*this.collision_layer.getTileWidth()));
    }

    private int YConverter( float y ){
        return (int)(y/(this.scale*this.collision_layer.getTileHeight()));
    }

}
