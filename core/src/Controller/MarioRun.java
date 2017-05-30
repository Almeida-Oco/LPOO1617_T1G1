package Controller;

/**
 * Created by oco on 5/29/17.
 */

//TODO 4th state when Mario is falling
public class MarioRun extends Mario {
    private final int ANIMATION_RATE = 5;
    private final int ANIMATION_RESET = 10;

    protected MarioRun(int x, int y) {
        super(x, y);
    }


    @Override
    public Mario moveMario(Map map, int x_move, int y_move) {
        if ( JUMP == y_move )
            return this.prepareJump(x_move);
        else if ( (GO_DOWN == y_move && checkLowerLadder(map)) || (GO_UP == y_move && checkUpperLadder(map)) )
            return new MarioClimb(this.position.getFirst(), this.position.getSecond() );

        this.updatePosition(map,x_move);
        this.updateSprite(x_move);
        this.tickTock();
        return this;
    }

    @Override
    protected void tickTock() {
        if ( ANIMATION_RESET == this.tick )
            this.tick = 0;

        this.tick++;
    }

    /**
     * @brief Checks if position of Mario is near ladder
     * @param map Current map of the game
     * @return Whether Mario is near a ladder or not
     */
    private boolean checkUpperLadder (Map map){
        return  map.nearLadder(this.position,this.rep_size) != -1;
    }

    private boolean checkLowerLadder (Map map){
        Pair<Integer,Integer> lower_pos = new Pair<Integer, Integer>(this.position.getFirst(),this.position.getSecond() -(int)(map.getMapTileHeight()*2));
        return map.nearLadder(lower_pos,this.rep_size) != -1;
    }

    /**
     * @brief Updates current sprite based on current status and sprite
     * @param direction Which direction Mario went
     */
    private void updateSprite(int direction) {
        if (GO_RIGHT == direction)
            this.movingRight();
        else if ( GO_LEFT == direction )
            this.movingLeft();
        else
            this.notMoving();
    }

    /**
     * @brief Updates mario position based on direction
     * @param map Current map of the game
     * @param x_move Direction to go
     */
    private void updatePosition( Map map , int x_move){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>( this.position.getFirst()+x_move*(int)this.velocity.getFirst().floatValue() ,
                                                                    this.position.getSecond()+(int)this.velocity.getSecond().floatValue());
        new_pos.setFirst( map.checkOutOfScreenWidth(new_pos.getFirst(), rep_size.getFirst()));
        new_pos.setSecond(map.checkOutOfScreenHeight(new_pos.getSecond(), rep_size.getSecond()));
        int new_y;
        if ( (new_y =  map.collidesBottom(new_pos,this.rep_size)) != -1){ //collided
            new_pos.setSecond(new_y);
            this.velocity.setSecond(0f);
        }
        else
            this.updateYVelocity();
        this.setPos(new_pos);
    }

    /**
     * @brief Makes Mario sprite be one where he goes right
     */
    private void movingRight(){
        if ( type.MARIO_RIGHT != this.current_type && type.MARIO_RUN_RIGHT != this.current_type )
            this.tick = 0;

        if ( this.tick < ANIMATION_RATE )
            this.current_type = type.MARIO_RIGHT;
        else
            this.current_type = type.MARIO_RUN_RIGHT;
    }

    /**
     * @brief Makes Mario sprite be one where he goes left
     */
    private void movingLeft(){
        if ( type.MARIO_LEFT != this.current_type && type.MARIO_RUN_LEFT != this.current_type )
            this.tick = 0;

        if ( this.tick < ANIMATION_RATE )
            this.current_type = type.MARIO_LEFT;
        else
            this.current_type = type.MARIO_RUN_LEFT;
    }

    /**
     * @brief Makes Mario sprite be one where he is standing still
     */
    private void notMoving(){
        if ( type.MARIO_RUN_LEFT == this.current_type )
            this.current_type = type.MARIO_LEFT;
        else if ( type.MARIO_RUN_RIGHT == this.current_type )
            this.current_type = type.MARIO_RIGHT;

        this.tick = 0;
    }

    private Mario prepareJump(int x_move){
        MarioJump ret = new MarioJump(this.position.getFirst() , this.position.getSecond() );
        if (x_move != 0)
            ret.setYVelocity( ret.getYSpeed()*x_move );
        else
            ret.setXVelocity(0);
        return ret;
    }

}
