package Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class Map {
    private final int SEARCH_TOP = 1;
    private final int SEARCH_RIGHT = 1;
    private final int CRANE_HORIZONTAL_TILES = 8;

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
        if (x < 0)
            return 0;
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
     * @brief Converts from TiledMap tile position to respective pixel position
     * @param map_pos Position of the Tile of the Map
     * @return Equivalent position in screen pixels
     * It always returns the correspondent bottom left corner of the tile
     */
    public Pair<Integer,Integer> mapPosToPixels( Pair<Integer,Integer> map_pos){
        return new Pair<Integer, Integer>(  (int)(map_pos.getFirst() * this.getMapTileWidth()),
                                            (int)(map_pos.getSecond() * this.getMapTileHeight()) );
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
                int temp_y = (int)(this.getEdgeHorizontalTileY(x,y,SEARCH_TOP) * this.getMapTileHeight() - 0.005f);
                if (temp_y > max_y)
                    max_y = temp_y;
            }

        return max_y;
    }

    public int collidesLeft( Pair<Integer,Integer> pos, int img_height){
        float x = pos.getFirst(), y_limit = pos.getSecond()+this.getMapTileHeight()*2,  y = pos.getSecond()+img_height;
        int max_x = -1;

        for (float step = this.getMapTileHeight() ; y > y_limit ; y-=step ){
            if ( isCellBlocked(x,y) ){
                int temp_x = (int)(this.getEdgeVerticalTileX(x,y,SEARCH_RIGHT) * this.getMapTileWidth()) + 1;
                if (temp_x > max_x)
                    max_x = temp_x;
            }
        }

        return max_x;
    }

    /**
     * @brief Checks if there is a ladder in the given position
     * @param x X coordinte of the object
     * @param y Y coordinate of the object
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


    /**
     * @brief Checks if there is a ladder below the given crane
     * @param pos Position in which to check for ladder
     * @param img_width Width of the image of the object querying about the ladder
     * @param delta Size of the interval of X coordinates to return, corresponds to the object X velocity
     * @return An interval of X coordinates where object should be in order to go down ladder, [-1,-1] if there is no ladder
     */
    public Pair<Integer,Integer> ladderInPosition(Pair<Integer,Integer> pos, int img_width, int delta){
        int x = this.XConverter(pos.getFirst()), y = this.YConverter(pos.getSecond()) - (CRANE_HORIZONTAL_TILES + 2);
        TiledMapTileLayer.Cell cell = ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell(x,y);
        if ( cell != null && cell.getTile() != null){
            int ret_x = (int)(x*this.getMapTileWidth()) + ((int)this.getMapTileWidth() - img_width);
            Pair<Integer,Integer> ret;
            if (delta % 2 == 0)
                ret = new Pair<Integer, Integer>(ret_x-delta/2, ret_x+delta/2);
            else
                ret = new Pair<Integer,Integer>(ret_x - (int)Math.floor(delta/2), ret_x + (int)Math.ceil(delta/2));

            return ret;
        }

        return new Pair<Integer,Integer>(-1,-1);
    }


    public void setScale( float scale ){
        this.scale = scale;
    }

    /**
     * @brief Gets the Y coordinate of the tile  which represents the edge/limit of this set of tiles
     * @param x X coordinate in which to search
     * @param y Y coordinate in which to start the search
     * @param inc Used to determine whether to search for lower edge (-1), or top edge (1)
     * @return The Y coordinate of the desired edge
     */
    private int getEdgeHorizontalTileY(float x , float y, int inc){
        int map_x = this.XConverter(x), map_y = this.YConverter(y);
        while ( this.collision_layer.getCell(map_x,map_y) != null)
            map_y+=inc;

        return map_y;
    }

    /**
     * @brief Gets the X coordinate of the tile which represents the edge/limit of this set of tiles
     * @param x X coordinate in which to start the search
     * @param y Y coordinate in which to search
     * @param inc Used to determine whether to search for leftmost edge (-1), or rightmost edge (1)
     * @return The X coordinate of the desired edge
     */
    private int getEdgeVerticalTileX (float x , float y, int inc){
        int map_x = this.XConverter(x), map_y = this.YConverter(y);
        while ( this.collision_layer.getCell(map_x,map_y) != null)
            map_x+=inc;

        return map_x;
    }

    public int XConverter( float x ){
        return (int)(x/this.getMapTileWidth());
    }

    public int YConverter( float y ){
        return (int)(y/this.getMapTileHeight());
    }
}
