package Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class Map {
    private TiledMap map;
    private TiledMapTileLayer collision_layer;
    private float scale = 1f;

    public TiledMap getMap(){
        return this.map;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collision_layer;
    }

    public float getMapTileWidth(){
        return (this.collision_layer.getTileWidth() * this.scale);
    }

    public float getMapTileHeight(){
        return (this.collision_layer.getTileHeight() * this.scale);
    }

    public TiledMapTileLayer.Cell getStairCell(int x , int y){
        return ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell( this.XConverter(x) , this.YConverter(y) );
    }

    public TiledMapTileLayer.Cell getFloorCell(int x , int y){
        return this.collision_layer.getCell( this.XConverter(x) , this.YConverter(y) );
    }

    public void dispose(){
        this.map.dispose();
    }

    /**
     * @brief Checks if given number is out of screen height bounds
     * @param y Number to check, represents Y coordinate of object
     * @param img_height Height of the image representing the object
     * @return If image is out of bounds then the closest coordinate possible to that bound, otherwise param pos
     */
    public int checkOutOfScreenHeight( int y , int img_height){
        if (y < 0)
            return 0;
        else if (y > (Gdx.graphics.getHeight()-img_height) )
            return (Gdx.graphics.getHeight()-img_height);
        else
            return y;
    }

    /**
     * @brief Checks if given number is out of screen width bounds
     * @param x Number to check, represents X coordinate of object
     * @param img_width Height of the image representing the object
     * @return If image is out of bounds then the closest coordinate possible to that bound, otherwise param pos
     */
    public int checkOutOfScreenWidth( int x, int img_width) {
        if (x < img_width/2)
            return img_width/2;
        else if (x > Gdx.graphics.getWidth() - img_width)
            return Gdx.graphics.getWidth() - img_width;
        else
            return x;
    }


    public void loadMap( String map_name, String collision_name ){
        this.map = (new TmxMapLoader()).load("maps/"+map_name);
        this.collision_layer = (TiledMapTileLayer)this.map.getLayers().get(collision_name);
    }



    /**
     * @brief Checks if the position collides with something below
     * @param pos Position to check
     * @param img_width Width of the image representing the object
     * @return The Y coordinate the object should go if it collides, -1 if it does not collide
     * It checks every bottom position of Mario in case it is colliding with multiple objects in order to get upper y coordinate
     */
    public int collidesBottom(Pair<Integer,Integer> pos, int img_width) {
        float x = pos.getFirst(), y = pos.getSecond(), x_limit = x+img_width;
        int max_y = -1;

        for( float step = this.getMapTileWidth() ; x < x_limit ;  x += step )
            if( isCellBlocked(x,y) ){
                int temp_y = (int)(this.getTopTile(x,y) * this.getMapTileHeight());
                if (temp_y > max_y)
                    max_y = temp_y;
            }

        return max_y;
    }

    /**
     * @brief Checks if there is a ladder in the given position
     * @param pos Position to check for ladder
     * @param rep_size Size of the representation of the object wanting to know if it is near ladder
     * @return X coordinate where the player should "teleport" to start climbing ladder, it is the middle of the ladder. -1 if no ladder is near
     */
    public int nearLadder(int x , int y){
        int     map_x = this.XConverter(x),
                map_y = this.YConverter(y);

        TiledMapTileLayer.Cell tile = ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell( map_x , map_y );
        if (tile != null && tile.getTile() != null)
            return (int)(map_x*this.getMapTileWidth());
        else
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
        int     map_x = this.XConverter(x),
                map_y = this.YConverter(y);
        TiledMapTileLayer.Cell cell = this.collision_layer.getCell(map_x , map_y);

        return (cell != null && cell.getTile() != null);
    }

    /**
     * @brief Checks if the ladder in the given position is usable
     * @param x X coordinate of the object
     * @param y Y coordinate of the object
     * @return Whether the object can use the ladder or not
     */
    public boolean canUseLadder(int x , int y){
        TiledMapTileLayer.Cell  floor = this.getFloorCell(x , y),
                                stair = this.getStairCell(x , y);

        return (floor != null && stair != null) || (floor == null && stair != null);
    }

    public void setScale( float scale ){
        this.scale = scale;
    }

    private int getTopTile(float x , float y){
        int map_x = this.XConverter(x), map_y = this.YConverter(y);
        while ( this.collision_layer.getCell(map_x,map_y) != null)
            map_y++;

        return map_y;
    }

    private int XConverter( float x ){
        return (int)(x/this.getMapTileWidth());
    }

    private int YConverter( float y ){
        return (int)(y/this.getMapTileHeight());
    }
}
