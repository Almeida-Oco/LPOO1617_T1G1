package Model;

class BarrelFall extends Barrel {
    private final int END_OF_MAP = 7;
    private final int ANIMATION_RATE = 5;
    private boolean fall_through;
    private boolean keep_falling;
    private boolean fire;

    /**
     *  Constructor for Barrel Fall
     * @param pos Position to create Barrel
     * @param x_dir Previous direction where the barrel was rolling
     * @param state Current state of barrel, [M,N], M -> if it is falling, N -> if it is always falling
     * If keep_falling is true, then fall_through will tell whether it is a fire_barrel or not
     */
    public BarrelFall(Pair<Integer,Integer> pos, Pair<Boolean, Boolean> state, int x_dir) {
        super(pos.getFirst(), pos.getSecond() ,x_dir);
        this.fall_through = state.getFirst();
        this.keep_falling = state.getSecond();
        this.fire = (fall_through && keep_falling);
        if ( this.fire )
            this.current_type = type.FIRE_BARREL_FALL_FRONT;
        else
            this.current_type = type.BARREL_FALL_FRONT;
    }

    @Override
    public Barrel moveEntity(Map map, int irrelevant1, int irrelevant2) {
        Barrel ret_val = updatePosition(map);
        this.tickTock();
        return ret_val;
    }

    /**
     *  Updates the position of the barrel based in collisions
     * @param map Current game map
     * @return This object if there should be no change in the Barrel state, BarrelRolling object otherwise
     */
    private Barrel updatePosition(Map map) {
        if (this.keep_falling)
            return this.moveFreeFallingBarrel(map);
        else
            return this.moveFallingBarrel(map);


    }

    //TODO maybe too many branches, separate into functions
    @Override
    protected void tickTock() {
        if (ANIMATION_RATE == this.tick){
            if ( !this.fire )
                if (type.BARREL_FALL_FRONT == this.current_type)
                   this.current_type = type.BARREL_FALL_BACK;
                else
                    this.current_type = type.BARREL_FALL_FRONT;
            else
                if( type.FIRE_BARREL_FALL_FRONT == this.current_type)
                    this.current_type = type.FIRE_BARREL_FALL_BACK;
                else
                    this.current_type = type.FIRE_BARREL_FALL_FRONT;

            this.tick=0;
        }
        this.tick++;
    }

    @Override
    public boolean toRemove(Map map) {
        return false;
    }

    @Override
    public void invertDirection() {}

    @Override
    public boolean canInvert() {
        return false;
    }

    /**
     *  Handles moving a Free Falling barrel
     * @param map Current game map
     * @return This object if there is no change in state, BarrelRolling otherwise
     */
    private Barrel moveFreeFallingBarrel(Map map){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>(this.position.getFirst(), this.position.getSecond() + this.getYSpeed() );
        int temp;
        if ( (temp = map.collidesBottom(new_pos,this.rep_size.getFirst())) != -1 && this.fall_through){
            this.setYVelocity( this.getYSpeed()/2 );
            this.fall_through = false;
        }
        else if ( temp == -1){
            this.updateYVelocity();
            this.fall_through = true;
        }
        this.setPos(new_pos);

        if ( map.YConverter(new_pos.getSecond()) <= END_OF_MAP )
            return new BarrelRolling(new_pos, this.fire,  -1);
        else
            return this;
    }

    /**
     *  Handles moving a barrel which is either going down stairs or falling to next cranes
     * @param map Current game map
     * @return This object if there is no change in state, BarrelRolling otherwise
     */
    private Barrel moveFallingBarrel(Map map){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>( this.position.getFirst(), this.position.getSecond() + this.getYSpeed() );
        int new_y;
        Barrel ret_val = this;
        if ( (new_y = map.collidesBottom(new_pos,this.rep_size.getFirst())) == -1 || this.fall_through ){
            if ( map.collidesBottom(new_pos,this.rep_size.getFirst()) == -1 )
                this.fall_through = false;
            this.setPos(new_pos);
            this.updateYVelocity();
        }
        else{
            new_pos.setSecond(new_y);
            ret_val = new BarrelRolling( new_pos, false, -this.x_direction);
        }


        return ret_val;
    }
}
