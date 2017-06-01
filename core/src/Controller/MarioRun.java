package Controller;


public class MarioRun extends Mario {
    private final int ANIMATION_RATE = 5;
    private final int ANIMATION_RESET = 10;

    /**
     * @brief Constructor for MarioRun
     * @param x X coordinate to position Mario
     * @param y Y coordinate to position Mario
     */
    protected MarioRun(int x, int y) {
        super(x, y);
        this.setYVelocity(0f);
    }


    @Override
    public Mario moveMario(Map map, int x_move, int y_move) {
        if ( JUMP == y_move )
            return this.prepareJump(x_move);
        else if ( (GO_DOWN == y_move && checkLowerLadder(map)) || (GO_UP == y_move && checkUpperLadder(map)) )
            return new MarioClimb(this.position.getFirst(), this.position.getSecond() );

        this.updateSprite(x_move);
        this.tickTock();
        return this.updatePosition(map,x_move);
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
        Pair<Integer,Integer> upper_pos = new Pair<Integer, Integer>(this.position.getFirst()+this.rep_size.getFirst()/2,
                this.position.getSecond() + (Math.round(map.getMapTileHeight())) );

        return  map.nearLadder(upper_pos.getFirst(), upper_pos.getSecond()) != -1;
    }

    /**
     * @brief Checks if there is a ladder below Mario
     * @param map Current map of the game
     * @return Whether Mario has a ladder below or not
     */
    private boolean checkLowerLadder (Map map){
        Pair<Integer,Integer> lower_pos = new Pair<Integer, Integer>(this.position.getFirst()+this.rep_size.getFirst()/2,
                this.position.getSecond() - Math.round(map.getMapTileHeight()) );

        return map.nearLadder(lower_pos.getFirst(), lower_pos.getSecond()) != -1;
    }

    /**
     * @brief Updates mario position based on direction
     * @param map Current map of the game
     * @param x_move Direction to go
     * @return Which mario state it should move to
     * TODO SMALLER IF NEEDED
     */
    private Mario updatePosition( Map map , int x_move){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>(this.position.getFirst()+x_move*this.getXSpeed() , this.position.getSecond());
        new_pos.setFirst (map.checkOutOfScreenWidth(new_pos.getFirst(), rep_size.getFirst()));
        new_pos.setSecond(map.checkOutOfScreenHeight(new_pos.getSecond(), rep_size.getSecond()));
        int new_y;
        Mario ret_val = this;
        if ( (new_y =  map.collidesBottom(new_pos,this.rep_size.getFirst())) != -1) { //collided
            new_pos.setSecond(new_y);
            this.setPos(new_pos);
        }
        else{
            ret_val = new MarioFall(new_pos.getFirst(), new_pos.getSecond());
            ret_val.setType(this.current_type);
        }

        return ret_val;
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
     * @brief Makes Mario sprite be one where he goes right
     */
    private void movingRight(){
        if ( type.MARIO_RIGHT != this.current_type && type.MARIO_RUN_RIGHT != this.current_type )
            this.tick = 0;

        if ( this.tick < ANIMATION_RATE )
            this.setType(type.MARIO_RIGHT);
        else
            this.setType(type.MARIO_RUN_RIGHT);
    }

    /**
     * @brief Makes Mario sprite be one where he goes left
     */
    private void movingLeft(){
        if ( type.MARIO_LEFT != this.current_type && type.MARIO_RUN_LEFT != this.current_type )
            this.tick = 0;

        if ( this.tick < ANIMATION_RATE)
            this.setType(type.MARIO_LEFT);
        else
            this.setType(type.MARIO_RUN_LEFT);
    }

    /**
     * @brief Makes Mario sprite be one where he is standing still
     */
    private void notMoving(){
        if ( type.MARIO_RUN_LEFT == this.current_type )
            this.setType(type.MARIO_LEFT);
        else if ( type.MARIO_RUN_RIGHT == this.current_type )
            this.setType(type.MARIO_RIGHT);

        this.tick = 0;
    }

    /**
     * @brief Used to set correct velocities before Mario starts jumping
     * @param x_move Direction where to jump
     * @return The newly created MarioJump object with correct velocities
     */
    private Mario prepareJump(int x_move){
        MarioJump ret = new MarioJump(this.position.getFirst() , this.position.getSecond() );
        if (x_move != 0)
            ret.setYVelocity( ret.getYSpeed()*x_move );
        else
            ret.setXVelocity(0);
        return ret;
    }

}
