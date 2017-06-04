package Controller;


public class MarioClimb extends Mario {
    private final int ANIMATION_RATE = 10;
    private final int CLIMB_RATE = 1;

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

    @Override
    public Entity moveEntity(Map map, int irrelevant, int y_move) {
        if (current_type == type.MARIO_DYING_UP)
            return new MarioDie(irrelevant, y_move);
        else {
            Mario ret_val = this;
            Pair<Integer, Integer> new_pos = this.getPos();
            if (GO_UP == y_move)
                new_pos = this.climbUp(map);
            else if (GO_DOWN == y_move)
                new_pos = this.climbDown(map);

            if (STAY_STILL != y_move)
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
        Pair<Integer, Integer> new_pos = new Pair<Integer, Integer>(this.position.getFirst(), this.position.getSecond() + CLIMB_RATE);
        Pair<Integer, Integer> upper_pos = new Pair<Integer, Integer>(new_pos.getFirst()+(int)(map.getMapTileWidth()/2), new_pos.getSecond() + (int)map.getMapTileHeight());
        int new_x;

        if ( (new_x = map.nearLadder(upper_pos.getFirst(), upper_pos.getSecond())) != -1 && map.canUseLadder(upper_pos.getFirst(), upper_pos.getSecond()) ){
            new_pos.setFirst(new_x-this.ladder_x_offset);
            return new_pos;
        }
        else
            return this.position;
    }

    /**
     *  Makes Mario climb down
     * @param map Current game map
     * @return Position after the climbing, if it has reached the end of stairs it returns the current position
     */
    private Pair<Integer,Integer> climbDown( Map map ){
        Pair<Integer,Integer> new_pos = new Pair<Integer,Integer>( this.position.getFirst() , this.position.getSecond() - CLIMB_RATE ),
                            lower_pos = new Pair<Integer, Integer>( new_pos.getFirst() + (int)(map.getMapTileWidth()/2) , new_pos.getSecond() - (int)map.getMapTileHeight() );
        int new_x;
        if ( ((new_x = map.nearLadder(lower_pos.getFirst(), this.getY())) != -1 || (new_x = map.nearLadder(lower_pos.getFirst(),lower_pos.getSecond())) != -1)
                    && (map.canUseLadder(new_pos.getFirst()+(int)(map.getMapTileWidth()/2), new_pos.getSecond()) || map.canUseLadder(lower_pos.getFirst(), lower_pos.getSecond())) ){
            new_pos.setFirst(new_x-this.ladder_x_offset);
            return new_pos;
        }
        else
            return this.position;
    }

    /**
     *  Used to process results of the climbing actions
     * @param map Current map of the game
     * @param new_pos New position returned by either climbUp() or climbDown()
     * @param y_move Direction Mario is trying to go
     * @return If end of ladder reached then a MarioRun object, this otherwise
     */
    private Mario processResults (Map map, Pair<Integer,Integer> new_pos){
        Pair<Integer,Integer> lower_pos = new Pair<Integer,Integer>(new_pos.getFirst()+this.rep_size.getFirst()/2, new_pos.getSecond() - (int)map.getMapTileHeight()); //force lower tile
        int new_y;
        if (this.position.equals(new_pos) && (new_y = map.collidesBottom(lower_pos, this.rep_size.getFirst())) != -1){
            Mario ret_val = new MarioRun( new_pos.getFirst(), new_y  );
            ret_val.setType(type.MARIO_CLIMB_OVER);
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
        else
            this.current_type = type.MARIO_CLIMB_LEFT;
    }
}
