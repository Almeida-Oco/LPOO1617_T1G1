package Controller;

public abstract class MoveStrategy {
    protected final int CLIMB_RATE = 1;
    protected final int UP = 1;
    protected final int DOWN = -1;
    protected final int RIGHT = 1;
    protected final int LEFT = -1;

    protected boolean first_cranes = true;
    protected boolean in_ladder = false;
    protected int tick = 0;
    protected int x_speed;
    protected int y_speed;
    protected Pair<Integer,Integer> rep_size;

    public abstract void move(Map map, Pair<Integer,Integer> mario_pos, Pair<Integer,Integer> curr_pos);

    public void setRep(Pair<Integer,Integer> rep){
        this.rep_size = rep;
    }

    public void setSpeed(Pair<Float,Float> speed){
        this.x_speed = (int)speed.getFirst().floatValue();
        this.y_speed = (int)speed.getSecond().floatValue();
    }

    /**
     * Moves the fire horizontally
     * @param map Current game map
     * @param curr_pos Current position of the fire
     * @param direction Direction to go
     */
    protected void moveHorizontally(Map map, Pair<Integer,Integer> curr_pos, int direction){
        curr_pos.setFirst( map.checkOutOfScreenWidth(curr_pos.getFirst()+(direction*this.x_speed), this.rep_size.getFirst()) );
        Pair<Integer,Integer>   lower_pos = new Pair<Integer,Integer>(curr_pos.getFirst(), curr_pos.getSecond() - (int)map.getMapTileHeight());
        int new_y;

        if ( (new_y = map.collidesBottom(curr_pos,rep_size.getFirst())) != -1 )
            curr_pos.setSecond(new_y);
        else if ( map.collidesBottom(lower_pos, rep_size.getFirst()) == -1)
            curr_pos.setSecond( curr_pos.getSecond() - (int)map.getMapTileHeight());
    }

    /**
     * Tries to move the fire vertically
     * @param map Current game map
     * @param curr_pos Current position of fire
     * @param direction Direction to go
     * @return True if there is still ladder, false if ladder ended
     */
    protected boolean moveVertically(Map map, Pair<Integer,Integer> curr_pos, int direction){
        int prev_y = curr_pos.getSecond();
        if ( UP == direction )
            this.moveUp(map,curr_pos);
        else if ( DOWN == direction )
            this.moveDown(map,curr_pos);

        this.in_ladder = (prev_y != curr_pos.getSecond());
        if ( !this.in_ladder )
            this.first_cranes = true;
        return this.in_ladder;
    }

    /**
     * Tries to move the fire down
     * @param map Current game map
     * @param pos New position to try to move into, if it is impossible to move there it is changed accordingly
     */
    private void moveDown(Map map, Pair<Integer,Integer> pos){
        pos.setSecond( pos.getSecond() - CLIMB_RATE);
        Pair<Integer,Integer>   middle_pos = new Pair<Integer,Integer>( pos.getFirst()+this.rep_size.getFirst()/2, pos.getSecond()),
                                lower_pos =  new Pair<Integer,Integer>( middle_pos.getFirst() , pos.getSecond() - (int)map.getMapTileHeight() );

        if ( map.collidesBottom(pos,this.rep_size.getFirst()) != -1 && !this.first_cranes )
            pos.setSecond( pos.getSecond() + CLIMB_RATE);

        if (map.collidesBottom(lower_pos,this.rep_size.getFirst()) == -1 && map.collidesBottom(middle_pos, this.rep_size.getFirst()) == -1)
            this.first_cranes = false;
    }

    /**
     * Tries to move the fire up
     * @param map Current game map
     * @param pos New position to try to move into, if it is impossible to move there it is changed accordingly
     */
    private void moveUp(Map map, Pair<Integer,Integer> pos){
        pos.setSecond( pos.getSecond() + CLIMB_RATE);
        Pair<Integer,Integer>   middle_pos = new Pair<Integer,Integer>( pos.getFirst()+this.rep_size.getFirst()/2, pos.getSecond()),
                            even_upper_pos = new Pair<Integer,Integer>( middle_pos.getFirst() , pos.getSecond() + (int)(map.getMapTileHeight()*2) );

        if ( map.collidesBottom(middle_pos,this.rep_size.getFirst()) == -1 && !this.first_cranes)
            pos.setSecond( pos.getSecond() - CLIMB_RATE );

        if ( map.collidesBottom(middle_pos, this.rep_size.getFirst()) != -1 && map.collidesBottom(even_upper_pos, this.rep_size.getFirst()) != -1 )
            this.first_cranes = false;

    }

    /**
     *  Checks if position of Fire is near ladder
     * @param map Current map of the game
     * @return Whether Fire is near a ladder or not
     */
    protected boolean checkUpperLadder(Map map, Pair<Integer,Integer> pos){
        Pair<Integer,Integer> upper_pos = new Pair<Integer, Integer>(pos.getFirst()+this.rep_size.getFirst()/2,
                pos.getSecond() + (Math.round(map.getMapTileHeight())) );

        return  map.nearLadder(upper_pos.getFirst(), upper_pos.getSecond()) != -1;
    }

    /**
     *  Checks if there is a ladder below Fire
     * @param map Current map of the game
     * @return Whether Fire has a ladder below or not
     */
    protected boolean checkLowerLadder(Map map, Pair<Integer,Integer> pos){
        Pair<Integer,Integer> lower_pos = new Pair<Integer, Integer>(pos.getFirst()+this.rep_size.getFirst()/2,
                pos.getSecond() - Math.round(map.getMapTileHeight()) );

        return map.nearLadder(lower_pos.getFirst(), lower_pos.getSecond()) != -1;
    }
}
