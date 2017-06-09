package Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class Map {
    private final int SEARCH_BOTTOM = -1;
    private final int SEARCH_TOP = 1;
    private final int SEARCH_RIGHT = 1;
    private final int CRANE_HORIZONTAL_TILES = 8;

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
     * Disposes the map
     */
    public void dispose(){
        this.map.dispose();
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
                int temp_x = (int)(this.getEdgeVerticalTileX(x,y,SEARCH_RIGHT) * this.getMapTileWidth()) + 1;
                if (temp_x > max_x)
                    max_x = temp_x;
            }
        }

        return max_x;
    }

    /**
     *  Checks if there is a ladder in the given position
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
        int map_x = this.XConverter(x), map_y = this.getEdgeHorizontalTileY(x,y-(this.getMapTileHeight()*2),SEARCH_BOTTOM)+1, closest_x = -1;
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
        int closest_x = -1, map_x = x;
        for ( ; x >= 0 ; x--){ //search left
            y=this.getEdgeHorizontalTileY(x*this.getMapTileWidth(),y*this.getMapTileHeight(),dir);
            if ( this.ladderIn(x, y+(2*dir)) && Math.abs(map_x - closest_x) > Math.abs(map_x - x) )
                closest_x = x;
        }
        return closest_x;
    }

    /**
     * Searches for a ladder on the right side of the object
     * @param x X coordinate to start search
     * @param y Y coordinate to start search
     * @param dir 1 if searching for top ladder, -1 if searching for lower ladder
     * @return X coordinate of closest ladder found
     */
    private int closestRightLadder(int x, int y, int dir){
        int closest_x = -1, map_x = x;
        for ( ; x <= this.collision_layer.getWidth() ; x++ ){
            y=this.getEdgeHorizontalTileY(x*this.getMapTileWidth(),y*this.getMapTileHeight(),dir);
            if ( this.ladderIn(x,y+(2*dir)) && Math.abs(map_x - closest_x) > Math.abs(map_x - x) )
                closest_x = x;
        }
        return closest_x;
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
        if ( this.onTheLeft(origin, dest) || this.onTheRight(origin,dest) )
            return true;
        //in case mario is jumping
        else if ( ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell(dest.getFirst(), dest.getSecond() ) == null ){
            int y = this.closestCrane(dest.getFirst(), dest.getSecond(), SEARCH_BOTTOM ) + 1;
            if ( y != 0 ){
                Pair<Integer,Integer> non_air_pos = new Pair<Integer, Integer>(dest.getFirst(), y);
                return this.onTheLeft(origin, non_air_pos) || this.onTheRight(origin, non_air_pos);
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
        int top_origin_y = this.closestCrane(origin.getFirst(), origin.getSecond() + 1, SEARCH_TOP) + CRANE_HORIZONTAL_TILES-1,
                lower_origin_y=this.closestCrane(origin.getFirst(), origin.getSecond() - CRANE_HORIZONTAL_TILES - 1, SEARCH_BOTTOM)+1;

        if ( top_origin_y != this.collision_layer.getHeight() ){   //found a top crane
            Pair<Integer,Integer>  temp_orig_top   = new Pair<Integer, Integer>( origin.getFirst(), top_origin_y );
            if ( this.onTheLeft(temp_orig_top, dest) || this.onTheRight(temp_orig_top,dest) )
                return 2;
        }
        if ( lower_origin_y != 0){ //found a bottom crane
            Pair<Integer,Integer> temp_orig_bottom= new Pair<Integer, Integer>( origin.getFirst(), lower_origin_y );
            if ( this.onTheLeft(temp_orig_bottom, dest) || this.onTheRight(temp_orig_bottom,dest) )
                return -2;
        }
        return 0;
    }

    /**
     * Checks if destination is in a ladder and if that ladder goes into origin level
     * @param origin Origin position
     * @param dest Destination position
     * @return 1 if on ladder connected to origin on bottom, -1 if on ladder connected to origin on top, 0 otherwise
     */

    private int ladderToOrigin( Pair<Integer,Integer> origin, Pair<Integer,Integer> dest ){
        int top_dest_y = this.closestCrane( dest.getFirst(), dest.getSecond(), SEARCH_TOP)  + CRANE_HORIZONTAL_TILES,
                lower_dest_y = this.closestCrane( dest.getFirst(), dest.getSecond(), SEARCH_BOTTOM ) + 1;
        // Ladder goes up to origin level
        if ( top_dest_y != this.collision_layer.getHeight() && ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell(dest.getFirst(), dest.getSecond()) != null ){
            Pair<Integer,Integer>  temp_dest_top   = new Pair<Integer, Integer>( dest.getFirst(), top_dest_y );
            if ( this.onTheLeft(origin, temp_dest_top) || this.onTheRight(origin, temp_dest_top) )
                return -1;
        }
        //Ladder goes down to origin level
        if ( lower_dest_y != 0 && ((TiledMapTileLayer)this.map.getLayers().get("Stairs")).getCell(dest.getFirst(), dest.getSecond()) != null){
            Pair<Integer,Integer> temp_dest_bottom = new Pair<Integer, Integer>( dest.getFirst(), lower_dest_y );
            if ( this.onTheLeft(origin, temp_dest_bottom) || this.onTheRight(origin, temp_dest_bottom) )
                return 1;
        }
        return 0;
    }

    /**
     * Checks if given destination is on the same level and on the left of origin
     * @param origin Origin position
     * @param destination Destination position
     * @return True if on same level and at the left, false otherwise
     */
    private boolean onTheLeft( Pair<Integer,Integer> origin, Pair<Integer,Integer> destination){
        int x = origin.getFirst(), y = origin.getSecond();
        for ( ; x >= 0 ; x--){ //search left
            if ( x%2 != 0 ){
                if ( this.collision_layer.getCell(x,y) != null && this.collision_layer.getCell(x,y+1) != null)
                    y++;
                else if ( this.collision_layer.getCell(x,y) == null )
                    y--;
            }
            if ( x == destination.getFirst() && (y+2) >= destination.getSecond() && (y+1-CRANE_HORIZONTAL_TILES) <= destination.getSecond() )
               return true;
        }
        return false;
    }

    /**
     * Checks if given destination is on the same level and on the right of origin
     * @param origin Origin position
     * @param destination Destination position
     * @return True if on same level and at the right, false otherwise
     */
    private boolean onTheRight( Pair<Integer,Integer> origin, Pair<Integer,Integer> destination){
        int x = origin.getFirst(), y = origin.getSecond();
        for ( ; x <= this.collision_layer.getWidth() ; x++ ){
            if ( x%2 == 0 ){
                if ( this.collision_layer.getCell(x,y) != null && this.collision_layer.getCell(x,y+1) != null)
                    y++;
                else if ( this.collision_layer.getCell(x,y) == null )
                    y--;
            }
            if ( x == destination.getFirst() && (y+2) >= destination.getSecond() && (y+1-CRANE_HORIZONTAL_TILES) <= destination.getSecond() )
                return true;
        }
        return false;
    }

    /**
     * Sets the map scaling
     * @param scale Scale of the map
     */
    public void setScale( float scale ){
        this.scale = scale;
    }

    //TODO put this in the test files, although it cannot be tested through JUnit
    public void testCheckLevelDifference(){
        //SAME LEVEL
        Pair<Integer,Integer> orig1 = new Pair<Integer,Integer>(Math.round(8 * this.getMapTileWidth()),Math.round(8*this.getMapTileHeight()));
        Pair<Integer,Integer> dest1 = new Pair<Integer,Integer>(Math.round(25 * this.getMapTileWidth()),Math.round(14*this.getMapTileHeight()));
        System.out.println( " RESULT1 = "+this.checkLevelDifference(orig1,dest1)+" EXPECTED 0");
        System.out.println( " RESULT2 = "+this.checkLevelDifference(dest1,orig1)+" EXPECTED 0");

        Pair<Integer,Integer> orig2 = new Pair<Integer,Integer>(Math.round(8 * this.getMapTileWidth()),Math.round(54*this.getMapTileHeight()));
        Pair<Integer,Integer> dest2 = new Pair<Integer,Integer>(Math.round(25 * this.getMapTileWidth()),Math.round(46*this.getMapTileHeight()));
        System.out.println( " RESULT3 = "+this.checkLevelDifference(orig2,dest2)+" EXPECTED 0");
        System.out.println( " RESULT4 = "+this.checkLevelDifference(dest2,orig2)+" EXPECTED 0");

        Pair<Integer,Integer> orig3 = new Pair<Integer,Integer>(Math.round(10 * this.getMapTileWidth()),Math.round(93*this.getMapTileHeight()));
        Pair<Integer,Integer> dest3 = new Pair<Integer,Integer>(Math.round(18 * this.getMapTileWidth()),Math.round(97*this.getMapTileHeight()));
        System.out.println( " RESULT5 = "+this.checkLevelDifference(orig3,dest3)+" EXPECTED 0");
        System.out.println( " RESULT6 = "+this.checkLevelDifference(dest3,orig3)+" EXPECTED 0");

        //UPPER/LOWER LEVEL
        Pair<Integer,Integer> orig4 = new Pair<Integer,Integer>(Math.round(7 * this.getMapTileWidth()),Math.round(8*this.getMapTileHeight()));
        Pair<Integer,Integer> dest4 = new Pair<Integer,Integer>(Math.round(14 * this.getMapTileWidth()),Math.round(51*this.getMapTileHeight()));
        System.out.println( " RESULT7 = "+this.checkLevelDifference(orig4,dest4)+" EXPECTED 2");
        System.out.println( " RESULT8 = "+this.checkLevelDifference(dest4,orig4)+" EXPECTED -2");

        Pair<Integer,Integer> orig5 = new Pair<Integer,Integer>(Math.round(14 * this.getMapTileWidth()),Math.round(51*this.getMapTileHeight()));
        Pair<Integer,Integer> dest5 = new Pair<Integer,Integer>(Math.round(10 * this.getMapTileWidth()),Math.round(93*this.getMapTileHeight()));
        System.out.println( " RESULT9 = "+ this.checkLevelDifference(orig5,dest5)+" EXPECTED 2");
        System.out.println( " RESULT10 = "+this.checkLevelDifference(dest5,orig5)+" EXPECTED -2");

        //STAIRS
        Pair<Integer,Integer> orig6 = new Pair<Integer,Integer>(Math.round(18 * this.getMapTileWidth()),Math.round((279-230)*this.getMapTileHeight()));
        Pair<Integer,Integer> dest6 = new Pair<Integer,Integer>(Math.round(12 * this.getMapTileWidth()),Math.round((279-209)*this.getMapTileHeight()));
        System.out.println( " RESULT11 = "+this.checkLevelDifference(orig6,dest6)+" EXPECTED 1");

        Pair<Integer,Integer> orig7 = new Pair<Integer,Integer>(Math.round(18 * this.getMapTileWidth()),Math.round((279-230)*this.getMapTileHeight()));
        Pair<Integer,Integer> dest7 = new Pair<Integer,Integer>(Math.round(23 * this.getMapTileWidth()),Math.round((279-250)*this.getMapTileHeight()));
        System.out.println( " RESULT12 = "+ this.checkLevelDifference(orig7,dest7)+" EXPECTED -1");

        //AT LEAST 2 LEVELS
        Pair<Integer,Integer> orig8 = new Pair<Integer,Integer>(Math.round(18 * this.getMapTileWidth()),Math.round((279-230)*this.getMapTileHeight()));
        Pair<Integer,Integer> dest8 = new Pair<Integer,Integer>(Math.round(7 * this.getMapTileWidth()),Math.round((279-102)*this.getMapTileHeight()));
        System.out.println( " RESULT13 = "+this.checkLevelDifference(orig8,dest8)+" EXPECTED 3");
        System.out.println( " RESULT14 = "+this.checkLevelDifference(dest8,orig8)+" EXPECTED -3");

        //DEST MID-AIR
        Pair<Integer,Integer> orig9 = new Pair<Integer,Integer>(Math.round(18 * this.getMapTileWidth()),Math.round((279-230)*this.getMapTileHeight()));
        Pair<Integer,Integer> dest9 = new Pair<Integer,Integer>(Math.round(8 * this.getMapTileWidth()),Math.round((279-212)*this.getMapTileHeight()));
        System.out.println( " RESULT15 = "+this.checkLevelDifference(orig9,dest9)+" EXPECTED 0");

        //ORIGIN MID-AIR
        Pair<Integer,Integer> orig10 = new Pair<Integer,Integer>(Math.round(4 * this.getMapTileWidth()),Math.round((279-208)*this.getMapTileHeight()));
        Pair<Integer,Integer> dest10 = new Pair<Integer,Integer>(Math.round(10 * this.getMapTileWidth()),Math.round((279-226)*this.getMapTileHeight()));
        Pair<Integer,Integer> dest11 = new Pair<Integer,Integer>(Math.round(12 * this.getMapTileWidth()),Math.round((279-185)*this.getMapTileHeight()));
        System.out.println( " RESULT16 = "+this.checkLevelDifference(orig10,dest10)+" EXPECTED 0");
        System.out.println( " RESULT17 = "+this.checkLevelDifference(orig10,dest11)+" EXPECTED 2");
    }


    /**
     *  Checks if there is a ladder below the given crane
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

    /**
     *  Gets the Y coordinate of the tile  which represents the edge/limit of this set of tiles
     * @param x X coordinate in which to search
     * @param y Y coordinate in which to start the search
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
