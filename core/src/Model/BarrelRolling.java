package Model;

import com.badlogic.gdx.Gdx;

public class BarrelRolling extends Barrel {
    private final int ANIMATION_RESET = 10;
    private final boolean FREE_FALL = false;
    private final boolean LADDER_FALL = true;
    private final int RIGHT = 1;
    private final int LEFT = -1;

    private Pair<Integer,Integer> fall_delta;
    private int prev_tile_x;
    private boolean fire;

    /**
     *  Constructor for rolling barrel
     * @param pos Position to create barrel
     * @param x_dir Direction in which barrel is supposed to move
     * @param fire Whether this barrel is of fire or nor
     */
    public BarrelRolling(Pair<Integer,Integer> pos, boolean fire,  int x_dir) {
        super(pos.getFirst(), pos.getSecond() , x_dir);
        this.current_type = type.BARREL_ROLLING1;
        this.prev_tile_x = -1;
        this.fall_delta = new Pair<Integer, Integer>(-1,-1);
        this.fire = fire;
    }

    @Override
    public Entity moveEntity(Map map, Pair<Integer,Integer> irrelevant) {
        this.checkNewDelta(map);
        Barrel ret_val = this.checkLadderFall(map);
        if (ret_val != this)
            return ret_val;
        else
            ret_val = updatePosition(map);

        if ( this.shouldInvertDirection(map) )
            this.x_direction*=-1;

        this.tickTock();
        return ret_val;
    }


    /**
     *  Used to check if there is a ladder nearby
     * @param map Current map of the game
     * If there is a ladder nearby fall_delta is set accordingly
     */
    private void checkNewDelta(Map map){
        int x_to_check = ( this.x_direction == -1 ) ? this.getX() : this.getX()+this.rep_size.getFirst(), tile_x = map.XConverter(x_to_check);
        if ( this.prev_tile_x != tile_x ){
            Pair<Integer,Integer> temp_delta = map.ladderInPosition(new Pair<Integer, Integer>(x_to_check,this.getY()), this.rep_size.getFirst(), this.getXSpeed() );
            if (temp_delta.getFirst() != -1)
                this.fall_delta = temp_delta;
            this.prev_tile_x = tile_x;
        }
    }

    /**
     *  Updates barrel position based on collisions
     * @param map Current game map
     * @return This object if state has not changed, BarrelFall otherwise
     */
    private Barrel updatePosition(Map map) {
        Pair<Integer,Integer>   new_pos = this.getNewPos(map), lower_pos = new Pair<Integer, Integer>( new_pos.getFirst(), new_pos.getSecond() - (int)map.getMapTileHeight()),
                                even_lower_pos = new Pair<Integer, Integer>( new_pos.getFirst(), new_pos.getSecond() - (int)map.getMapTileHeight()*3);

        if ( map.collidesBottom(lower_pos, this.rep_size.getFirst()) == -1)
            new_pos.setSecond( new_pos.getSecond() - (int)map.getMapTileHeight() );
        else if ( map.collidesBottom(new_pos, this.rep_size.getFirst()) != -1)
            new_pos.setSecond( new_pos.getSecond() + (int)map.getMapTileHeight() );

        if ( map.collidesBottom(even_lower_pos, this.rep_size.getFirst()) == -1){
            Barrel ret = new BarrelFall(new_pos, new Pair<Boolean, Boolean>(FREE_FALL,false), this.x_direction);
            ret.setRepSize( this.rep_size.getFirst(), this.rep_size.getSecond(), this.scale );
            return ret;
        }

        this.setPos(new_pos);
        return this;
    }

    /**
     * Gets next position of the barrel
     * @param map Current game map
     * @return Next position of the barrel
     */
    private Pair<Integer,Integer> getNewPos(Map map){
        Pair<Integer,Integer> new_pos = this.getPos();
        float x_speed = (this.inverted) ? -(this.x_direction*this.getXSpeed()) : (this.x_direction*this.getXSpeed());
        new_pos.setFirst(new_pos.getFirst()+(int)x_speed);
        new_pos.setFirst (map.checkOutOfScreenWidth(new_pos.getFirst(), rep_size.getFirst()));
        new_pos.setSecond(map.checkOutOfScreenHeight(new_pos.getSecond(), rep_size.getSecond()));
        return new_pos;
    }

    /**
     *  Sequence of sprites when the barrel is rolling to the right
     */
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

    /**
     *  Sequence of sprites when the barrel is rolling to the left
     */
    private void updateSpriteLeft(){
        if ( !this.fire ){
            if (type.BARREL_ROLLING1 == this.current_type)
                this.current_type = type.BARREL_ROLLING4;
            else if (type.BARREL_ROLLING2 == this.current_type)
                this.current_type = type.BARREL_ROLLING1;
            else if (type.BARREL_ROLLING3 == this.current_type)
                this.current_type = type.BARREL_ROLLING2;
            else if (type.BARREL_ROLLING4 == this.current_type)
                this.current_type = type.BARREL_ROLLING3;
        }
        else
            this.current_type = type.FIRE_BARREL_ROLLING;

    }

    /**
     *  Checks if there is any ladder for the Barrel to fall through
     * @param map Current game map
     * @return This object if there is no ladder to fall, BarrelFall object otherwise
     */
    private Barrel checkLadderFall(Map map){
        int curr_x = this.getX(), lower_y = this.getY()-(int)map.getMapTileHeight();
        if ( curr_x >= this.fall_delta.getFirst() && curr_x <= this.fall_delta.getSecond() && (Math.random()*10) > 6){
            Barrel ret = new BarrelFall(new Pair<Integer,Integer>(curr_x, lower_y), new Pair<Boolean,Boolean>(LADDER_FALL, false) , this.x_direction);
            ret.setRepSize( this.rep_size.getFirst(), this.rep_size.getSecond(), this.scale );
            return ret;
        }
        else if ( curr_x >= this.fall_delta.getFirst() && curr_x <= this.fall_delta.getSecond() ){
            this.fall_delta.setFirst(-1);
            this.fall_delta.setSecond(-1);
        }

        return this;
    }


    private boolean shouldInvertDirection(Map map){
        if ( (this.getX() == 0 || this.getX() == (Gdx.graphics.getWidth()-this.rep_size.getFirst()) ||
                map.collidesLeft(this.position, this.rep_size.getSecond()) != -1) && this.inverted) {
            this.inverted = false;
            return true;
        }
        return false;
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

    @Override
    public void invertDirection() {
        if ( this.canInvert() ){
            this.n_times_inverted++;
            this.inverted = true;
        }
    }

    @Override
    public boolean canInvert() {
        return (this.n_times_inverted == 0);
    }

    @Override
    public boolean toRemove(Map map){
        int map_x = map.XConverter(this.getX()), map_y = map.YConverter(this.getY());
        if ( !this.fire )
            return (map_y <= 8 && this.getX() <= (3*map.getMapTileWidth()+map.getMapTileWidth()/4) );
        else
            return (map_x <= 1 && map_y <= 25);
    }

    @Override
    public void upgrade() {
        this.invertDirection();
    }
}
