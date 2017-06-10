package Model;

public class MarioJump extends Mario {
    private int direction = -10;
    private float jump_x_vel;

    /**
     *  Constructor for MarioJump
     * @param x X coordinate to position Mario
     * @param y Y coordinate to position Mario
     */
    public MarioJump(int x , int y ){
        super(x,y);
        this.setYVelocity(JUMP_VELOCITY);
        this.jump_x_vel = this.getXSpeed()*1.5f;
    }

    /**
     * Moves jumping mario
     * @param map Current map of the game
     * @param move First contains x direction to go, second is irrelevant
     * @return
     */
    @Override
    public Model.Entity moveEntity(Map map, Pair<Integer,Integer> move) {
        if (current_type == type.MARIO_DYING_UP)
            return new MarioDie( position.getFirst(), position.getSecond() );
        else {
            Mario ret_val;
            if (this.direction == -10)
                this.direction = move.getFirst();

            ret_val = this.updatePosition(map);
            ret_val.setType(this.current_type);
            this.tickTock();
            return ret_val;
        }
    }

    @Override
    protected void tickTock() {
        this.updateYVelocity();
    }

    /**
     *  Updates Mario position based on direction of jump
     * @param map Current map of the game
     * @return MarioRun object if the jump has ended, this object with updated position otherwise
     */
    private Mario updatePosition(Map map){
        Mario ret_val = this;
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>( this.position.getFirst()+ (int)this.jump_x_vel*this.direction,
                                                                    this.position.getSecond() + this.getYSpeed() );
        int new_y, new_x;
        if ( (new_y = map.collidesBottom(new_pos,this.rep_size.getFirst())) != -1){
            this.setYVelocity(0f);
            new_pos.setSecond(new_y);
            ret_val = new MarioRun(new_pos.getFirst(), new_pos.getSecond());
            ret_val.setXVelocity( (int)(this.jump_x_vel/1.5) );
        }
        if ( (new_x = map.checkOutOfScreenWidth(new_pos.getFirst(),this.rep_size.getFirst())) != -1)
            new_pos.setFirst(new_x);
        this.setPos(new_pos);
        return ret_val;
    }
}
