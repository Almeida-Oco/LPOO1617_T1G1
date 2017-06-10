package Model;

public abstract class MoveStrategy {
    protected final int CLIMB_RATE = 1;
    protected final int UP = 1;
    protected final int DOWN = -1;
    protected final int RIGHT = 1;
    protected final int LEFT = -1;

    private boolean first_cranes = true;
    protected int previous_dir = -10;
    protected boolean in_ladder = false;
    protected int tick = 0;
    protected int x_speed;
    protected int y_speed;
    protected Pair<Integer,Integer> rep_size;

    public abstract void move(Model.Map map, Pair<Integer,Integer> mario_pos, Pair<Integer,Integer> curr_pos);

    public abstract MoveStrategy improveStrategy();

    public void setRep(Pair<Integer,Integer> rep){
        this.rep_size = rep;
    }

    public void setSpeed(Pair<Float,Float> speed){
        this.x_speed = (int)speed.getFirst().floatValue();
        this.y_speed = (int)speed.getSecond().floatValue();
    }

    public boolean inLadder(){
        return this.in_ladder;
    }

    /**
     * Moves the fire horizontally
     * @param map Current game map
     * @param curr_pos Current position of the fire
     * @param direction Direction to go
     */
    protected void moveHorizontally(Model.Map map, Pair<Integer,Integer> curr_pos, int direction){
        curr_pos.setFirst( map.checkOutOfScreenWidth(curr_pos.getFirst()+(direction*this.x_speed), this.rep_size.getFirst()) );
        Pair<Integer,Integer>   lower_pos = new Pair<Integer,Integer>(curr_pos.getFirst(), curr_pos.getSecond() - (int)map.getMapTileHeight());
        int new_y;

        if ( (new_y = map.collidesBottom(curr_pos,rep_size.getFirst())) != -1 )
            curr_pos.setSecond(new_y);
        else if ( map.collidesBottom(lower_pos, rep_size.getFirst()) == -1)
            curr_pos.setSecond( curr_pos.getSecond() - (int)map.getMapTileHeight());
        this.first_cranes = true;
        this.previous_dir = direction;
    }

    /**
     * Tries to move the fire vertically
     * @param map Current game map
     * @param curr_pos Current position of fire
     * @param direction Direction to go
     * @return True if there is still ladder, false if ladder ended
     */
    protected boolean moveVertically(Model.Map map, Pair<Integer,Integer> curr_pos, int direction){
        if ( map.collidesBottom(curr_pos, this.rep_size.getFirst()) != -1 || !this.in_ladder )
            this.first_cranes = true;
        this.previous_dir = direction;

        if ( UP == direction )
            this.moveUp(map,curr_pos);
        else if ( DOWN == direction )
            this.moveDown(map,curr_pos);


        return this.in_ladder = ( !this.notInLadder(map,curr_pos) );
    }

    /**
     * Tries to move the fire down
     * @param map Current game map
     * @param pos New position to try to move into, if it is impossible to move there it is changed accordingly
     */
    private void moveDown(Model.Map map, Pair<Integer,Integer> pos){
        int img_width = this.rep_size.getFirst(), lower_x;
        Pair<Integer,Integer>   middle_pos = new Pair<Integer,Integer>( pos.getFirst()+img_width/4, pos.getSecond()),
                                lower_pos =  new Pair<Integer,Integer>( middle_pos.getFirst() , pos.getSecond() - (int)map.getMapTileHeight() );
        pos.setSecond( pos.getSecond() - CLIMB_RATE);
        if ( (lower_x = map.collidesBottom(lower_pos,img_width/2)) != -1 && !this.first_cranes )
            pos.setSecond( pos.getSecond() + CLIMB_RATE);
        else if ( lower_x == -1 )
            this.first_cranes = false;
    }

    /**
     * Tries to move the fire up
     * @param map Current game map
     * @param pos New position to try to move into, if it is impossible to move there it is changed accordingly
     */
    private void moveUp(Model.Map map, Pair<Integer,Integer> pos){
        int img_width = this.rep_size.getFirst(), lower_x;
        Pair<Integer,Integer>   middle_pos = new Pair<Integer,Integer>( pos.getFirst()+img_width/4, pos.getSecond()),
                                lower_pos  = new Pair<Integer,Integer>( middle_pos.getFirst() , pos.getSecond() - (int)map.getMapTileHeight() );
        pos.setSecond( pos.getSecond() + CLIMB_RATE);
        if (  (lower_x = map.collidesBottom(lower_pos,img_width/2)) != -1 && map.collidesBottom(middle_pos,img_width/2) == -1 && !this.first_cranes )
            pos.setSecond( pos.getSecond() - CLIMB_RATE);
        else if ( lower_x == -1 )
            this.first_cranes = false;

    }

    private boolean notInLadder(Model.Map map, Pair<Integer,Integer> pos){
        int img_width = this.rep_size.getFirst();
        Pair<Integer,Integer> middle_pos = new Pair<Integer, Integer>(pos.getFirst()+img_width/4, pos.getSecond()),
                            lower_pos = new Pair<Integer, Integer>(middle_pos.getFirst(), middle_pos.getSecond() - (int)map.getMapTileHeight() );

        return ( map.collidesBottom(middle_pos,img_width/2) == -1 && map.collidesBottom(lower_pos,img_width/2) != -1 );
    }

    /**
     *  Checks if position of Fire is near ladder
     * @param map Current map of the game
     * @return Whether Fire is near a ladder or not
     */
    protected boolean checkUpperLadder(Model.Map map, Pair<Integer,Integer> pos){
        Pair<Integer,Integer> upper_pos = new Pair<Integer, Integer>(pos.getFirst()+this.rep_size.getFirst()/2,
                pos.getSecond() + (Math.round(map.getMapTileHeight())) );

        return  map.nearLadder(upper_pos.getFirst(), upper_pos.getSecond()) != -1;
    }

    /**
     *  Checks if there is a ladder below Fire
     * @param map Current map of the game
     * @return Whether Fire has a ladder below or not
     */
    protected boolean checkLowerLadder(Model.Map map, Pair<Integer,Integer> pos){
        Pair<Integer,Integer> lower_pos = new Pair<Integer, Integer>(pos.getFirst()+this.rep_size.getFirst()/2,
                pos.getSecond() - Math.round(map.getMapTileHeight()) );

        return map.nearLadder(lower_pos.getFirst(), lower_pos.getSecond()) != -1;
    }
}
