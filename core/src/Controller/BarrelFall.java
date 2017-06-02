package Controller;

class BarrelFall extends Barrel {
    private final int END_OF_MAP = 7;
    private final int ANIMATION_RATE = 5;
    private boolean fall_through;
    private boolean keep_falling;

    /**
     * @brief Constructor for Barrel Fall
     * @param x X coordinate to create barrel
     * @param y Y coordinate to create barrel
     * @param x_dir Previous direction where the barrel was rolling
     * @param fall_through Whether the barrel is moving to the next set of cranes or not. Also signals if barrel is of fire or not
     * @param keep_falling Whether the barrel is supposed to keep falling until end of map
     * If keep_falling is true, then fall_through will tell whether it is a fire_barrel or not
     */
    public BarrelFall(int x, int y, int x_dir, boolean fall_through, boolean keep_falling) {
        super(x,y,x_dir);
        this.current_type = type.BARREL_FALL_FRONT;
        this.fall_through = fall_through;
        this.keep_falling = keep_falling;
    }

    @Override
    public Barrel moveEntity(Map map, int irrelevant1, int irrelevant2) {
        Barrel ret_val = updatePosition(map);
        this.tickTock();
        return ret_val;
    }

    /**
     * @brief Updates the position of the barrel based in collisions
     * @param map Current game map
     * @return This object if there should be no change in the Barrel state, BarrelRolling object otherwise
     */
    private Barrel updatePosition(Map map) {
        if (this.keep_falling)
            return this.moveFreeFallingBarrel(map);
        else
            return this.moveFallingBarrel(map);


    }

    @Override
    protected void tickTock() {
        if (ANIMATION_RATE == this.tick){
            if (type.BARREL_FALL_FRONT == this.current_type)
                this.current_type = type.BARREL_FALL_BACK;
            else
                this.current_type = type.BARREL_FALL_FRONT;
            this.tick=0;
        }
        this.tick++;
    }

    @Override
    public void invertDirection() {}

    @Override
    public boolean canInvert() {
        return false;
    }

    /**
     * @brief Handles moving a Free Falling barrel
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
            return new BarrelRolling(new_pos.getFirst(), new_pos.getSecond(), -1);
        else
            return this;
    }

    /**
     * @brief Handles moving a barrel which is either going down stairs or falling to next cranes
     * @param map Current game map
     * @return This object if there is no change in state, BarrelRolling otherwise
     */
    private Barrel moveFallingBarrel(Map map){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>(this.position.getFirst(), this.position.getSecond() + this.getYSpeed() );
        int new_y;
        Barrel ret_val = this;
        if ( (new_y = map.collidesBottom(new_pos,this.rep_size.getFirst())) == -1 || this.fall_through ){
            if ( map.collidesBottom(new_pos,this.rep_size.getFirst()) == -1 )
                this.fall_through = false;
            this.setPos(new_pos);
            this.updateYVelocity();
        }
        else
            ret_val = new BarrelRolling( new_pos.getFirst() , new_y , -this.x_direction);

        return ret_val;
    }
}
