package Controller;

public class BarrelRolling extends Barrel {
    private final int ANIMATION_RESET = 10;
    private final boolean FREE_FALL = false;
    private final boolean LADDER_FALL = true;
    private final int RIGHT = 1;
    private final int LEFT = -1;

    private Pair<Integer,Integer> fall_delta;
    private int prev_tile_x;

    public BarrelRolling(int x, int y, int x_dir) {
        super(x,y,x_dir);
        this.current_type = type.BARREL_ROLLING1;
        this.prev_tile_x = -1;
        this.fall_delta = new Pair<Integer, Integer>(-1,-1);
    }

    @Override //x_move and y_move irrelevant
    public Entity moveEntity(Map map, int x_move, int y_move) {
        this.checkNewDelta(map);
        Barrel ret_val = this.checkLadderFall(map);
        if (ret_val != this)
            return ret_val;
        else
            ret_val = updatePosition(map);

        this.tickTock();
        return ret_val;
    }


    /**
     * @brief Used to check if there is a ladder nearby
     * @param map Current map of the game
     * If there is a ladder nearby fall_delta is set accordingly
     */
    private void checkNewDelta(Map map){
        int x_to_check = ( this.x_direction == -1 ) ? this.getX() : this.getX()+this.rep_size.getFirst(),
                tile_x = map.XConverter(x_to_check);
        if ( this.prev_tile_x != tile_x ){
            Pair<Integer,Integer> temp_delta = map.ladderInPosition(new Pair<Integer, Integer>(x_to_check,this.getY()), this.rep_size.getFirst(), this.getXSpeed() );
            if (temp_delta.getFirst() != -1)
                this.fall_delta = temp_delta;
            this.prev_tile_x = tile_x;
        }
    }

    private Barrel updatePosition(Map map){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>(this.position.getFirst()+ this.x_direction*this.getXSpeed() , this.position.getSecond());
        new_pos.setFirst( map.checkOutOfScreenWidth(new_pos.getFirst(), rep_size.getFirst()));
        new_pos.setSecond(map.checkOutOfScreenHeight(new_pos.getSecond(), rep_size.getSecond()));
        Barrel ret_val = this;
        if ( map.collidesBottom(new_pos, this.rep_size.getFirst()) != -1)
            this.setPos(new_pos);
        else
            ret_val = this.checkCraneSlope(new_pos,map);

        return ret_val;
    }


    private void updateSpriteRight(){
        if (type.BARREL_ROLLING1 == this.current_type)
            this.current_type = type.BARREL_ROLLING2;
        else if (type.BARREL_ROLLING2 == this.current_type)
            this.current_type = type.BARREL_ROLLING3;
        else if (type.BARREL_ROLLING3 == this.current_type)
            this.current_type = type.BARREL_ROLLING4;
        else if (type.BARREL_ROLLING4 == this.current_type)
            this.current_type = type.BARREL_ROLLING1;
    }

    private void updateSpriteLeft(){
        if (type.BARREL_ROLLING1 == this.current_type)
            this.current_type = type.BARREL_ROLLING4;
        else if (type.BARREL_ROLLING2 == this.current_type)
            this.current_type = type.BARREL_ROLLING1;
        else if (type.BARREL_ROLLING3 == this.current_type)
            this.current_type = type.BARREL_ROLLING2;
        else if (type.BARREL_ROLLING4 == this.current_type)
            this.current_type = type.BARREL_ROLLING3;
    }

    /**
     * @brief Checks whether the barrel is about to free fall or if it is just a crane slope
     * @param map Current map of the game
     * @return This object if it is just a slope, BarrelFall object if it is a free fall
     */
    private Barrel checkCraneSlope(Pair<Integer,Integer> new_pos, Map map ) {
        Pair<Integer, Integer> lower_pos = new Pair<Integer, Integer>(new_pos.getFirst(), new_pos.getSecond() - (int)((map.getMapTileHeight()*2)));
        if (map.collidesBottom(lower_pos, this.rep_size.getFirst()) == -1)
            return new BarrelFall(new_pos.getFirst(),new_pos.getSecond(),this.x_direction, FREE_FALL );

        new_pos.setSecond( new_pos.getSecond() - (int)map.getMapTileHeight());
        this.setPos(new_pos);
        return this;
    }

    /**
     * @brief Checks if there is any ladder for the Barrel to fall through
     * @param map Current game map
     * @return This object if there is no ladder to fall, BarrelFall object otherwise
     */
    private Barrel checkLadderFall(Map map){
        int curr_x = this.getX(), lower_y = getY()-(int)map.getMapTileHeight();
        if ( curr_x >= this.fall_delta.getFirst() && curr_x <= this.fall_delta.getSecond() && (Math.random()*10+1) > 7)
            return new BarrelFall(curr_x, lower_y , this.x_direction, LADDER_FALL );
        else if ( curr_x >= this.fall_delta.getFirst() && curr_x <= this.fall_delta.getSecond() ){
            this.fall_delta.setFirst(-1);
            this.fall_delta.setSecond(-1);
        }

        return this;
    }

    @Override
    protected void tickTock() {
        if ( ANIMATION_RESET == this.tick )
            this.tick = 0;

        if ( this.tick == 0 ){
            if ( this.x_direction == RIGHT)
                this.updateSpriteRight();
            else if ( this.x_direction == LEFT)
                this.updateSpriteLeft();
        }

        this.tick++;

    }
}
