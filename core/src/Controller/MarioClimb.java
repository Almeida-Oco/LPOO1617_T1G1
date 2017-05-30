package Controller;

/**
 * Created by oco on 5/29/17.
 */

public class MarioClimb extends Mario {
    private final int ANIMATION_RATE = 10;
    private final int CLIMB_RATE = 1;

    public MarioClimb(int x, int y) {
        super(x, y);
        this.tick = 0;
    }

    @Override
    public Mario moveMario(Map map, int x_move, int y_move) {
        Pair<Integer,Integer> new_pos = this.getPos();
        if ( GO_UP == y_move )
            new_pos = this.climbUp( map );
        else if (GO_DOWN == y_move )
            new_pos = this.climbDown( map );

        if (this.position.equals(new_pos) && STAY_STILL != y_move) //End of stairs reached
            return new MarioRun( new_pos.getFirst(), new_pos.getSecond() );
        else
            this.tickTock();

        this.setPos(new_pos);
        return this;
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
     * @brief Makes Mario climb up
     * @param map Current game map
     * @return Position after the climbing, if it has reached the end of stairs it returns the current position
     */
    private Pair<Integer,Integer> climbUp( Map map ) {
        Pair<Integer, Integer> new_pos = new Pair<Integer, Integer>(this.position.getFirst(), this.position.getSecond() + CLIMB_RATE);
        Pair<Integer, Integer> upper_pos = new Pair<Integer, Integer>(new_pos.getFirst(), new_pos.getSecond() + (int)map.getMapTileHeight()); //force upper tile
        int new_x = 0;

        //there is still ladder above Mario
        if ( (new_x = map.nearLadder(this.position, this.rep_size)) != -1 && map.nearLadder(upper_pos,this.rep_size) != -1){
            new_pos.setFirst(new_x);
            return new_pos;
        }
        else
            return this.position;
    }

    /**
     * @brief Makes Mario climb down
     * @param map Current game map
     * @return Position after the climbing, if it has reached the end of stairs it returns the current position
     */
    private Pair<Integer,Integer> climbDown( Map map ){
        Pair<Integer,Integer> new_pos = new Pair<Integer,Integer>( this.position.getFirst() , this.position.getSecond() - CLIMB_RATE );
        Pair<Integer,Integer> lower_pos = new Pair<Integer,Integer>(new_pos.getFirst(), new_pos.getSecond() - (int)map.getMapTileHeight()); //force lower tile
        int new_x = 0;

        //there is still ladder below Mario
        if ( ((new_x=map.nearLadder(lower_pos,this.rep_size)) != -1) && map.ladderAndCraneBelow(lower_pos,this.rep_size) ){
            new_pos.setFirst(new_x);
            return new_pos;
        }

        else
            return this.position;
    }

    /**
     * @brief updates current Mario sprite/status based on current status
     * TODO if there are more types needed in here do HashMap<type,type>
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
