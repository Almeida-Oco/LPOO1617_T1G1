package Controller;

class BarrelFall extends Barrel {
    private final int ANIMATION_RATE = 5;
    private boolean fall_through = false;

    public BarrelFall(int x, int y, int x_dir, boolean fall_through) {
        super(x,y,x_dir);
        this.current_type = type.BARREL_FALL_FRONT;
        this.fall_through = fall_through;
    }

    @Override //x_move and y_move irrelevant
    public Barrel moveEntity(Map map, int x_move, int y_move) {
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
}
