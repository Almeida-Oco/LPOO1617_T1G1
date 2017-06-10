package Model;


public class MarioClimb extends Mario {
    private final int ANIMATION_RATE = 10;
    private final int CLIMB_RATE = 1;
    private int ladder_x_offset = 0;

    /**
     *  Constructor for MarioClimb
     * @param x X coordinate to position Mario
     * @param y Y coordinate to position Mario
     */
    public MarioClimb(int x, int y) {
        super(x, y);
        this.tick = 0;
        this.current_type = type.MARIO_CLIMB_LEFT;
    }

    /**
     * Moves climbing mario
     * @param map Current map of the game
     * @param move First is irrelevant, second contains direction to climb
     * @return
     */
    @Override
    public Model.Entity moveEntity(Map map, Pair<Integer,Integer> move) {
        int y_move = move.getSecond();
        if (current_type == type.MARIO_DYING_UP)
            return new MarioDie(position.getFirst(), position.getSecond());
        else {
            Mario ret_val = this;
            Pair<Integer, Integer> new_pos = this.getPos();
            if (GO_UP == y_move)
                new_pos = this.climbUp(map);
            else if (GO_DOWN == y_move)
                new_pos = this.climbDown(map);

            if (y_move == GO_DOWN || y_move == GO_UP) //if stay still no need to process
                ret_val = processResults(map, new_pos);
            this.setPos(new_pos);
            return ret_val;
        }
    }

    @Override
    protected void tickTock() {
        if ( ANIMATION_RATE == this.tick )
            this.tick = 0;

        if ( 0 == this.tick )
            this.updateSprite();
        this.tick++;
    }

    /**
     *  Makes Mario climb up
     * @param map Current game map
     * @return Position after the climbing, if it has reached the end of stairs it returns the current position
     */
    private Pair<Integer,Integer> climbUp( Map map ) {
        Pair<Integer, Integer> new_pos = new Pair<Integer, Integer>(this.getX(), this.getY() + CLIMB_RATE);
        Pair<Integer, Integer> upper_pos = new Pair<Integer, Integer>(new_pos.getFirst()+this.rep_size.getFirst()/4, this.getY() + (int)map.getMapTileHeight());

        if ( map.nearLadder(upper_pos.getFirst(), upper_pos.getSecond()) != -1 && map.canUseLadder(upper_pos.getFirst(), upper_pos.getSecond()) )
            return new_pos;

        return this.position;
    }

    /**
     *  Makes Mario climb down
     * @param map Current game map
     * @return Position after the climbing, if it has reached the end of stairs it returns the current position
     */
    private Pair<Integer,Integer> climbDown( Map map ){
        Pair<Integer,Integer> new_pos = new Pair<Integer,Integer>( this.position.getFirst() , this.position.getSecond() - CLIMB_RATE ),
                            lower_pos = new Pair<Integer, Integer>( new_pos.getFirst() + this.rep_size.getFirst()/4 , new_pos.getSecond() - (int)map.getMapTileHeight() );

        if ( (map.nearLadder(lower_pos.getFirst(), this.getY()) != -1 || map.nearLadder(lower_pos.getFirst(),lower_pos.getSecond()) != -1)
                    && (map.canUseLadder(lower_pos.getFirst(), new_pos.getSecond()) || map.canUseLadder(lower_pos.getFirst(), lower_pos.getSecond())) )
            return new_pos;

        return this.position;
    }

    /**
     *  Used to process results of the climbing actions
     * @param map Current map of the game
     * @param new_pos New position returned by either climbUp() or climbDown()
     * @return If end of ladder reached then a MarioRun object, this otherwise
     */
    private Mario processResults (Map map, Pair<Integer,Integer> new_pos){
        Pair<Integer,Integer> lower_pos = new Pair<Integer,Integer>(new_pos.getFirst()+this.rep_size.getFirst()/4, new_pos.getSecond() - (int)map.getMapTileHeight()); //force lower tile
        //collision because of incomplete ladders
        if (this.position.equals(new_pos) && map.collidesBottom(lower_pos, this.rep_size.getFirst()/2) != -1 ){
            Mario ret_val = new MarioRun( this.getX(), this.getY()  );
            ret_val.setType(type.MARIO_CLIMB_OVER);
            ret_val.setRepSize(this.rep_size.getFirst(), this.rep_size.getSecond(), this.scale);
            return ret_val;
        }

        this.tickTock();
        return this;
    }

    /**
     *  updates current Mario sprite/status based on current status
     */
    private void updateSprite(){
        if ( type.MARIO_CLIMB_LEFT == this.current_type )
            this.current_type = type.MARIO_CLIMB_RIGHT;
        else if ( type.MARIO_CLIMB_RIGHT == this.current_type )
            this.current_type = type.MARIO_CLIMB_LEFT;
//        else
//            this.current_type = type.MARIO_CLIMB_LEFT;
    }
}
