package Controller;

/**
 * Created by oco on 5/30/17.
 */

public class MarioJump extends Mario {
    private int direction = -10;
    private final float JUMP_VELOCITY = 12f;

    public MarioJump(int x , int y ){
        super(x,y);
        this.setYVelocity(JUMP_VELOCITY);
    }

    @Override
    public Mario moveMario(Map map, int x_move, int y_move) {
        Mario ret_val;
        if (this.direction == -10)
            this.direction = x_move;

        ret_val = this.updatePosition(map,x_move,y_move);

        this.tickTock();
        return ret_val;
    }

    @Override
    protected void tickTock() {
        this.updateYVelocity();
    }

    private Mario updatePosition(Map map, int x_move, int y_move){
        Mario ret_val = this;
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>( this.position.getFirst()+this.getXSpeed()*this.direction,
                                                                    this.position.getSecond()+(int)this.getYSpeed() );
        int new_y, new_x;
        if ( (new_y = map.collidesBottom(new_pos,this.rep_size)) != -1){
            this.setYVelocity(0f);
            new_pos.setSecond(new_y);
            ret_val = new MarioRun(this.position.getFirst(), this.position.getSecond());
            ret_val.setXVelocity(-1); //sets to default value
        }
        if ( (new_x = map.checkOutOfScreenWidth(new_pos.getFirst(),this.rep_size.getFirst())) != -1)
            new_pos.setFirst(new_x);
        this.setPos(new_pos);
        return ret_val;
    }
}
