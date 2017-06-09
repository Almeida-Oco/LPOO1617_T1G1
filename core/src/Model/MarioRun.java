package Model;


public class MarioRun extends Mario {
    private final int ANIMATION_RATE = 5;
    private final int ANIMATION_RESET = 10;

    /**
     *  Constructor for MarioRun
     * @param x X coordinate to position Mario
     * @param y Y coordinate to position Mario
     */
    protected MarioRun(int x, int y) {
        super(x, y);
        this.setYVelocity(0f);
    }

    /**
     * Moves mario
     * @param map Current map of the game
     * @param move Contains the movement in X coordinate and Y coordinate
     * @return
     */
    @Override
    public Model.Entity moveEntity(Map map, Pair<Integer,Integer> move) {
        int x_move = move.getFirst(), y_move = move.getSecond(), new_x;
        if (current_type == type.MARIO_DYING_UP)
            return new MarioDie(position.getFirst(), position.getSecond());
        else {
            if (JUMP == y_move)
                return this.prepareJump(x_move);
            else if ( (GO_DOWN == y_move && ((new_x =checkLowerLadder(map)) != -1)) || (GO_UP == y_move && ((new_x=checkUpperLadder(map))) != -1 ) )
                return this.prepareClimb(map.getMapTileWidth(), new_x);

            this.updateSprite(x_move);
            this.tickTock();
            return this.updatePosition(map, x_move);
        }
    }

    @Override
    protected void tickTock() {
        if ( ANIMATION_RESET == this.tick )
            this.tick = 0;

        this.tick++;
    }

    /**
     *  Updates mario position based on direction
     * @param map Current map of the game
     * @param direction Direction to go
     * @return Which mario state it should move to
     */
    private Mario updatePosition( Map map , int direction){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>( map.checkOutOfScreenWidth(this.position.getFirst()+direction*this.getXSpeed(), rep_size.getFirst()),
                                                     map.checkOutOfScreenHeight(this.position.getSecond(), rep_size.getSecond()) );

        int new_x;
        if ( (new_x = map.collidesLeft(new_pos,this.rep_size.getSecond())) != -1 )
            new_pos.setFirst(new_x);

        Mario ret_val = this.processY(map,new_pos);
        ret_val.setPos(new_pos);
        return ret_val;
    }

    /**
     *  Checks if there is any event to take care of in the Y coordinate
     * @param new_pos New position of Mario
     * @return This object or an object of MarioFall if a state change is necessary
     */
    private Mario processY(Map map,Pair<Integer,Integer> new_pos){
        Pair<Integer,Integer> lower_pos = new Pair<Integer, Integer>(new_pos.getFirst(), new_pos.getSecond() - (int)map.getMapTileHeight() ),
                        even_lower_pos = new Pair<Integer, Integer>(new_pos.getFirst(), new_pos.getSecond() - (int)map.getMapTileHeight()*2 );
        Mario ret_val = this;
        int new_y;
        if ( (new_y =  map.collidesBottom(new_pos,this.rep_size.getFirst())) != -1)
            new_pos.setSecond(new_y);
        else if ( map.collidesBottom(lower_pos,this.rep_size.getFirst()) == -1 )
            new_pos.setSecond( new_pos.getSecond() - (int)map.getMapTileHeight() );

        if ( map.collidesBottom(even_lower_pos, this.rep_size.getFirst()) == -1){
            ret_val = new MarioFall(new_pos.getFirst(), new_pos.getSecond());
            ret_val.setType(this.current_type);
        }

        return ret_val;
    }

    /**
     *  Updates current sprite based on current status and sprite
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
     *  Makes Mario sprite be one where he goes right
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
     *  Makes Mario sprite be one where he goes left
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
     *  Makes Mario sprite be one where he is standing still
     */
    private void notMoving(){
        if ( type.MARIO_RUN_LEFT == this.current_type )
            this.setType(type.MARIO_LEFT);
        else if ( type.MARIO_RUN_RIGHT == this.current_type )
            this.setType(type.MARIO_RIGHT);

        this.tick = 0;
    }

    /**
     *  Used to set correct velocities before Mario starts jumping
     * @param x_move Direction where to jump
     * @return The newly created MarioJump object with correct velocities
     */
    private Model.Entity prepareJump(int x_move){
        Entity ret = new MarioJump(this.position.getFirst() , this.position.getSecond() );
        if (x_move != 0)
            ret.setXVelocity( ret.getXSpeed()*x_move );
        else
            ret.setXVelocity(0);

        ret.setType(this.current_type);
        ret.setRepSize( this.rep_size.getFirst(), this.rep_size.getSecond(), this.scale );
        return ret;
    }

    /**
     * Prepares mario for climbing
     * @param tile_width Width of tiled map tiles
     * @param ladder_x X position of ladder mario is near
     * @return A newly create MarioClimb object with centered X coordinate
     */
    private Model.Entity prepareClimb(float tile_width, int ladder_x){
        ladder_x += (int)((tile_width - this.rep_size.getFirst())/2);
        Entity ret = new MarioClimb(ladder_x, this.getY() );
        ret.setRepSize( this.rep_size.getFirst(), this.rep_size.getSecond(), this.scale );
        return ret;
    }

    /**
     *  Checks if position of Mario is near ladder
     * @param map Current map of the game
     * @return -1 if Mario is not on ladder, X position of ladder otherwise
     */
    private int checkUpperLadder(Map map){
        Pair<Integer,Integer> upper_pos = new Pair<Integer, Integer>(this.position.getFirst()+this.rep_size.getFirst()/2 ,
                this.position.getSecond() + (Math.round(map.getMapTileHeight())) );

        return  map.nearLadder(upper_pos.getFirst(), upper_pos.getSecond());
    }

    /**
     *  Checks if there is a ladder below Mario
     * @param map Current map of the game
     * @return -1 if Mario is not on ladder, X position of ladder otherwise
     */
    private int checkLowerLadder(Map map){
        Pair<Integer,Integer> lower_pos = new Pair<Integer, Integer>(this.position.getFirst()+this.rep_size.getFirst()/2 ,
                this.position.getSecond() - (int)(map.getMapTileHeight()*2) );

        return map.nearLadder(lower_pos.getFirst(), lower_pos.getSecond());
    }
}
