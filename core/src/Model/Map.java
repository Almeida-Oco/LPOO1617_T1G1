package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class Map {
    private final int SEARCH_BOTTOM = -1;
    private final int SEARCH_TOP = 1;
    private final int SEARCH_RIGHT = 1;
    private final int SEARCH_LEFT = -1;

    private TiledMap map;
    private TiledMapTileLayer collision_layer;
    private float scale = 1f;

    /**
     * Gets the TiledMap
     * @return Class member map
     */
    public TiledMap getMap(){
        return this.map;
    }

    /**
     * Gets the collision layer of the map
     * @return Class member collision_layer
     */
    public TiledMapTileLayer getCollisionLayer() {
        return collision_layer;
    }

    /**
     * Gets the TiledMap tile width based on current scale
     * @return Width of tiles
     */
    public float getMapTileWidth(){
        return (this.collision_layer.getTileWidth() * this.scale);
    }

    /**
     * Gets the TiledMap tile height based on current scale
     * @return Height of tiles
     */
    public float getMapTileHeight(){
        return (this.collision_layer.getTileHeight() * this.scale);
    }

    /**
     * Gets the given cell from the Stair layer
     * @param x X coordinate in pixels of object
     * @param y Y coordinate in pixels of object
     * @return The correspondent cell
     */
    private TiledMapTileLayer.Cell getStairCell(int x , int y){
        return ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell( this.XConverter(x) , this.YConverter(y) );
    }

    /**
     * Gets the given cell from the Floor layer
     * @param x X coordinate in pixels of object
     * @param y Y coordinate in pixels of object
     * @return The correspondent cell
     */
    private TiledMapTileLayer.Cell getFloorCell(int x , int y){
        return this.collision_layer.getCell( this.XConverter(x) , this.YConverter(y) );
    }

    /**
     *  Checks if given number is out of screen height bounds
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
     *  Checks if given number is out of screen width bounds
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

    /**
     * Loads the map
     * @param map_name Name of the .tmx file
     * @param collision_name Name of the collision layer
     */
    public void loadMap( String map_name, String collision_name ){
        this.map = (new TmxMapLoader()).load("maps/"+map_name);
        this.collision_layer = (TiledMapTileLayer)this.map.getLayers().get(collision_name);
    }

    public void setMap( TiledMap map, TiledMapTileLayer collision_layer ){
        this.map = map;
        this.collision_layer = collision_layer;
    }

    /**
     *  Converts from TiledMap tile position to respective pixel position
     * @param map_pos Position of the Tile of the Map
     * @return Equivalent position in screen pixels
     * It always returns the correspondent bottom left corner of the tile
     */
    public Pair<Integer,Integer> mapPosToPixels(Pair<Integer,Integer> map_pos){
        return new Pair<Integer, Integer>(  (int)(map_pos.getFirst() * this.getMapTileWidth()),
                                            (int)(map_pos.getSecond() * this.getMapTileHeight()) );
    }

    /**
     *  Checks if the position collides with something below
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
                int temp_y = (int)( this.getEdgeHorizontalTileY(x,y,SEARCH_TOP) * this.getMapTileHeight() /*- 0.005f+*/ );
                if (temp_y > max_y)
                    max_y = temp_y;
            }

        return max_y;
    }

    /**
     *  Checks if the position collides with something on the left
     * @param pos Position to check
     * @param img_height Height of the image representing the object
     * @return The X coordinate the object should go if it collides, -1 if it does not collide
     * It checks every left position of Mario in case it is colliding with multiple objects in order to get highest x coordinate
     */
    public int collidesLeft( Pair<Integer,Integer> pos, int img_height){
        float x = pos.getFirst(), y_limit = pos.getSecond()+this.getMapTileHeight()*2,  y = pos.getSecond()+img_height;
        int max_x = -1;

        for (float step = this.getMapTileHeight()/2 ; y > y_limit ; y-=step ){
            if ( isCellBlocked(x,y) ){
                int temp_x = (int)(this.getEdgeVerticalTileX(x,y,SEARCH_RIGHT) * this.getMapTileWidth());
                if (temp_x > max_x)
                    max_x = temp_x;
            }
        }

        return max_x;
    }

    /**
     *  Checks if there is a ladder in the given position
     * @param x X coordinate of the object
     * @param y Y coordinate of the object
     * @return X coordinate where the player should will go to start climbing ladder, it is the middle of the ladder. -1 if no ladder is near
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
     *  Checks if corresponding Map cell is blocked
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
     *  Checks if the ladder in the given position is usable
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
     * Searches for the closest lower stair from the given start position
     * @param x X coordinate in pixels of object
     * @param y Y coordinate in pixels of object
     * @return X coordinate in Tiles of closest lower ladder
     */
    public int closestLowerStair(int x, int y){
        int map_x = this.XConverter(x), map_y = this.getEdgeHorizontalTileY(x,y-(this.getMapTileHeight()*2),SEARCH_BOTTOM), closest_x = -1;
        closest_x = this.closestLeftLadder(map_x,map_y, SEARCH_BOTTOM );
        int temp_x = this.closestRightLadder(map_x, map_y, SEARCH_BOTTOM );
        if ( Math.abs(map_x - closest_x) >= Math.abs(map_x - temp_x) )
            closest_x = temp_x;

        return closest_x;
    }

    /**
     * Searches for the closest upper stair from the given start position
     * @param x X coordinate in pixels of object
     * @param y Y coordinate in pixels of object
     * @return X coordinate in Tiles of closest upper ladder
     */
    public int closestUpperStair(int x , int y){
        int map_x = this.XConverter(x), map_y = this.getEdgeHorizontalTileY(x,y-(this.getMapTileHeight()*2), SEARCH_TOP), closest_x;
        closest_x = this.closestLeftLadder(map_x, map_y, SEARCH_TOP);
        int temp_x = this.closestRightLadder(map_x, map_y, SEARCH_TOP);
        if ( Math.abs(map_x - closest_x) > Math.abs(map_x - temp_x) )
            closest_x = temp_x;

        return closest_x;
    }

    /**
     * Searches for a ladder on the left side of object
     * @param x X Tiled coordinate to start search
     * @param y Y Tiled coordinate to start search
     * @param dir 1 If searching for top ladder, -1 if searching for lower ladder
     * @return X coordinate of closest ladder found
     */
    private int closestLeftLadder(int x , int y , int dir){
        for ( ; x >= 0 ; x-- ){
            if ( this.collision_layer.getCell(x,y-dir) == null )
                y-=dir;
            else if ( this.collision_layer.getCell(x,y) != null )
                y = this.getEdgeHorizontalTileY(x*getMapTileWidth(),y*getMapTileHeight(),dir);

            if ( this.collision_layer.getCell(x,y-dir) == null ) //no more crane
                return -1;
            else if  ( this.ladderIn(x,y+(2*dir)) )
                return x;
        }
        return -1;
    }

    /**
     * Searches for a ladder on the right side of the object
     * @param x X coordinate to start search
     * @param y Y coordinate to start search
     * @param dir 1 if searching for top ladder, -1 if searching for lower ladder
     * @return X coordinate of closest ladder found
     */
    private int closestRightLadder(int x, int y, int dir){
        for ( ; x <= this.collision_layer.getWidth() ; x++ ){
            if ( this.collision_layer.getCell(x,y-dir) == null )
                y-=dir;
            else if ( this.collision_layer.getCell(x,y) != null )
                y = this.getEdgeHorizontalTileY(x*getMapTileWidth(),y*getMapTileHeight(),dir);

            if ( this.collision_layer.getCell(x,y-dir) == null ) //no more crane
                return -1;
            else if  ( this.ladderIn(x,y+(2*dir)) )
                return x;
        }
        return -1;
    }

    /**
     * Checks if there is a ladder in the given position
     * @param x X coordinate in tiles to search
     * @param y Y coordinate in tiles to search
     * @return Whether there is a ladder or not
     */
    private boolean ladderIn(int x , int y){
        return ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell(x,y) != null;
    }


    /**
     * Compares given positions and returns a number that represents where the destination position is
     * @param origin The position to start looking
     * @param dest The position to try to reach
     * @return 0 -> same level, 1 upper ladder connected to level, 2 upper level, 3 at least 2 upper levels, -1 lower ladder connected to level, -2 lower level, -3 at least 2 bottom levels
     */
    public int checkLevelDifference(Pair<Integer,Integer> origin, Pair<Integer,Integer> dest){
        Pair<Integer,Integer> origin_tiled = new Pair<Integer, Integer>( this.XConverter(origin.getFirst()) , this.YConverter(origin.getSecond()) ),
                                dest_tiled = new Pair<Integer, Integer>( this.XConverter(dest.getFirst()), this.YConverter(dest.getSecond()) );

        int ret;
        origin_tiled.setSecond( this.floorOrCeilOrigin(origin_tiled.getFirst(), origin_tiled.getSecond()) );
        if ( this.sameLevel(origin_tiled, dest_tiled) )
            return 0;
        if ( (ret = this.searchTopAndBottomLevel(origin_tiled,dest_tiled)) != 0)
            return ret;
        if ( (ret = this.ladderToOrigin(origin_tiled, dest_tiled)) != 0)
            return ret;

        if ( origin_tiled.getSecond() > dest_tiled.getSecond() ) //Destination is at least 2 levels up or down
            return -3;
        else
            return 3;
    }

    private int floorOrCeilOrigin(int x, int y){
        if ( this.collision_layer.getCell(x,y) != null )
            return this.closestCrane(x,y,SEARCH_TOP);
        else
            return this.closestCrane(x,y,SEARCH_BOTTOM)+1;
    }

    /**
     * Checks if destination and origin are on the same level, taking into consideration that dest might be in mid-air
     * @param origin Origin position
     * @param dest Destination position
     * @return Whether the given positions are in the same level or not
     */
    private boolean sameLevel( Pair<Integer,Integer> origin, Pair<Integer,Integer> dest){
        if ( this.searchInDirection(origin, dest, SEARCH_LEFT) || this.searchInDirection(origin,dest, SEARCH_RIGHT) )
            return true;
        //in case mario is jumping
        else if ( ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell(dest.getFirst(), dest.getSecond() ) == null ){
            int y = this.closestCrane(dest.getFirst(), dest.getSecond(), SEARCH_BOTTOM ) + 1;
            if ( y != 0 ){
                Pair<Integer,Integer> non_air_pos = new Pair<Integer, Integer>(dest.getFirst(), y);
                return this.searchInDirection(origin, non_air_pos, SEARCH_LEFT) || this.searchInDirection(origin, non_air_pos,SEARCH_RIGHT);
            }

        }

        return false;
    }

    /**
     * Searches if destination position is on a top or bottom level
     * @param origin Origin position
     * @param dest Destination
     * @return 2 If it is in a top level, -2 if it is on a bottom level, 0 otherwise
     */
    private int searchTopAndBottomLevel( Pair<Integer,Integer> origin, Pair<Integer,Integer> dest ){
        int top_origin_y = this.getNearLevelY(origin, SEARCH_TOP), lower_origin_y = this.getNearLevelY(origin, SEARCH_BOTTOM),
                x = origin.getFirst();

        if ( top_origin_y < this.collision_layer.getHeight() ){   //found a top crane
            Pair<Integer,Integer>  temp_orig_top   = new Pair<Integer, Integer>( x, top_origin_y );
            if ( this.searchInDirection(temp_orig_top, dest, SEARCH_LEFT) || this.searchInDirection(temp_orig_top,dest, SEARCH_RIGHT) )
                return 2;
        }
        if ( lower_origin_y > 0){ //found a bottom crane
            Pair<Integer,Integer> temp_orig_bottom= new Pair<Integer, Integer>( x, lower_origin_y );
            if ( this.searchInDirection(temp_orig_bottom, dest, SEARCH_LEFT) || this.searchInDirection(temp_orig_bottom, dest, SEARCH_RIGHT) )
                return -2;
        }
        return 0;
    }

    /**
     * Gets the next crane level Y coordinate
     * @param origin Position to start the search, must be the tile immediately above the crane of the current level
     * @param dir Search for upper level (1) , lower level (-1)
     * @return Y coordinate of the crane immediately up or down the crane specified by origin
     */
    private int getNearLevelY( Pair<Integer,Integer> origin, int dir ){
        int x = origin.getFirst(), y = origin.getSecond();
        if ( dir == SEARCH_TOP )
            return this.getEdgeHorizontalTileY((int)(x*getMapTileWidth()), (int)(this.closestCrane(x, y, SEARCH_TOP)*getMapTileHeight()) ,SEARCH_TOP);
        else if ( dir == SEARCH_BOTTOM )
            return this.closestCrane(x , this.getEdgeHorizontalTileY((int)(x*getMapTileWidth()),(int)((y-2)*getMapTileHeight()),SEARCH_BOTTOM)-1, SEARCH_BOTTOM );
        else
            return -1;
    }

    /**
     * Checks if destination is in a ladder and if that ladder goes into origin level
     * @param origin Origin position
     * @param dest Destination position
     * @return 1 if on ladder connected to origin on bottom, -1 if on ladder connected to origin on top, 0 otherwise
     */
    private int ladderToOrigin( Pair<Integer,Integer> origin, Pair<Integer,Integer> dest ){
        int dest_x = dest.getFirst(), dest_y = dest.getSecond();
        int top_dest_y = this.getNearLevelY(dest, SEARCH_TOP), lower_dest_y = this.getNearLevelY(dest, SEARCH_BOTTOM);
        // Ladder goes up to origin level
        if ( top_dest_y != this.collision_layer.getHeight() && ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell(dest_x, dest_y) != null ){
            Pair<Integer,Integer>  temp_dest_top   = new Pair<Integer, Integer>( dest_x, top_dest_y );
            if ( this.searchInDirection(origin, temp_dest_top,SEARCH_LEFT) || this.searchInDirection(origin, temp_dest_top,SEARCH_RIGHT) )
                return -1;
        }
        //Ladder goes down to origin level
        if ( lower_dest_y != 0 && ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell(dest_x, dest_y) != null){
            Pair<Integer,Integer> temp_dest_bottom = new Pair<Integer, Integer>( dest_x, lower_dest_y );
            if ( this.searchInDirection(origin, temp_dest_bottom,SEARCH_LEFT) || this.searchInDirection(origin, temp_dest_bottom,SEARCH_RIGHT) )
                return 1;
        }
        return 0;
    }

    /**
     * Searches for dest in the given direction, following current crane
     * @param origin Position to start looking
     * @param dest Position to look for
     * @param dir Direction to search, 1 right, -1 left
     * @return True if dest was found, false otherwise
     */
    private boolean searchInDirection(Pair<Integer,Integer> origin, Pair<Integer,Integer> dest, int dir){
        int x = origin.getFirst(), y = origin.getSecond(), dest_x = dest.getFirst(), dest_y = dest.getSecond();
        for ( ; ((dir == SEARCH_LEFT) ? (x >= 0) : (x <= this.collision_layer.getWidth())) ; x+=dir ){
            if ( this.collision_layer.getCell(x,y-1) == null )
                y--;
            else if ( this.collision_layer.getCell(x,y) != null )
                y = this.getEdgeHorizontalTileY(x,y,SEARCH_TOP);

            if ( this.collision_layer.getCell(x,y-1) == null ) //no more crane
                return false;
            else if  ( x == dest_x && (y+2) >= dest_y && (y - this.craneThickness(new Pair<Integer, Integer>(x,y-1),SEARCH_BOTTOM)+1 ) <= dest_y )
                return true;
        }
        return false;
    }

    /**
     * Calculates the thickness of the crane
     * @param origin Initial top position of the crane to start the calculation
     * @param dir Direction to search, 1 = top, -1 = bottom
     * @return The thickness of the specified crane
     */
    private int craneThickness( Pair<Integer,Integer> origin, int dir ){
        int x = origin.getFirst(), y = origin.getSecond(), init_y = y;
        while ( this.collision_layer.getCell(x,y) != null && y >= 0 && y <= this.collision_layer.getHeight() )
            y+=dir;

        return Math.abs(y-init_y);
    }

    /**
     * Sets the map scaling
     * @param scale Scale of the map
     */
    public void setScale( float scale ){
        this.scale = scale;
    }


    /**
     *  Checks if there is a ladder below the given crane
     * @param pos Position in which to check for ladder
     * @param img_width Width of the image of the object querying about the ladder
     * @param x_speed Size of the interval of X coordinates to return, corresponds to the object X velocity
     * @return An interval of X coordinates where object should be in order to go down ladder, [-1,-1] if there is no ladder
     */
    public Pair<Integer,Integer> ladderInPosition(Pair<Integer,Integer> pos, int img_width, int x_speed){
        int x = this.XConverter(pos.getFirst()), y = this.getEdgeHorizontalTileY(pos.getFirst(), pos.getSecond() - (int)this.getMapTileHeight()*2, SEARCH_BOTTOM );
        TiledMapTileLayer.Cell cell = ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell(x,y);
        if ( cell != null && cell.getTile() != null){
            int ret_x = (int)(x*this.getMapTileWidth()) + ((int)this.getMapTileWidth() - img_width);
            Pair<Integer,Integer> ret;
            if (x_speed % 2 == 0)
                ret = new Pair<Integer, Integer>(ret_x-x_speed/2, ret_x+x_speed/2);
            else
                ret = new Pair<Integer,Integer>(ret_x - (int)Math.floor(x_speed/2), ret_x + (int)Math.ceil(x_speed/2));

            return ret;
        }

        return new Pair<Integer,Integer>(-1,-1);
    }

    /**
     *  Gets the Y coordinate of the tile  which represents the edge/limit of this set of tiles
     * @param x X pixel coordinate in which to search
     * @param y Y pixel coordinate in which to start the search
     * @param inc Used to determine whether to search for lower edge (-1), or top edge (1)
     * @return The Y coordinate of the edge immediately above/below the desired edge
     */
    private int getEdgeHorizontalTileY(float x , float y, int inc){
        int map_x = this.XConverter(x), map_y = this.YConverter(y);
        while ( this.collision_layer.getCell(map_x,map_y) != null)
            map_y+=inc;

        return map_y;
    }

    /**
     *  Gets the X coordinate of the tile which represents the edge/limit of this set of tiles
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

    /**
     * Searches vertically for the closest crane in the given direction
     * @param x X tile coordinate to start the search
     * @param y Y tile coordinate to start the search
     * @param dir Direction to search, 1 UP , 2 DOWN
     * @return Y tiled coordinate of closest horizontal tile found
     * Ignores first cranes found in case object is currently in the middle of cranes
     */
    private int closestCrane(int x , int y, int dir){
        while( this.collision_layer.getCell(x,y) == null && y < this.collision_layer.getHeight() && y >= 0)
            y+=dir;

        return y;
    }

    /**
     * Converts from pixel coordinates to tile coordinates
     * @param x X coordinate in pixels
     * @return X coordinate in tiles
     */
    public int XConverter( float x ){
        return (int)(x/this.getMapTileWidth());
    }

    /**
     * Converts from pixel coordinates to tile coordinates
     * @param y Y coordinate in pixels
     * @return Y coordinate in tiles
     */
    public int YConverter( float y ){
        return (int)(y/this.getMapTileHeight());
    }
}
