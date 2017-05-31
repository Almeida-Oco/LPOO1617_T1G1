package Controller;

/**
 * Created by asus on 31/05/2017.
 */

class BarrelFall extends Barrel {
    private int direction = -10;

    public BarrelFall(int x, int y) {
        super(x,y);
    }

    @Override
    public Barrel moveBarrel(Map map, int x_move, int y_move) {
        if (-10 == this.direction)
            this.direction = x_move;

        Barrel ret_val = updatePosition(map);
        this.tickTock();

        return ret_val;
    }

    private Barrel updatePosition(Map map) {
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>(this.position.getFirst(), this.position.getSecond() + this.getYSpeed() );
        int new_y;
        Barrel ret_val = this;
        if ( (new_y = map.collidesBottom(new_pos,this.rep_size)) != -1 ){
            this.setPos(new_pos);
            this.updateYVelocity();
        }
        else{
            ret_val = new BarrelRolling( new_pos.getSecond() , new_y );
            ret_val.setYVelocity(0f);
        }

        return ret_val;
    }

    @Override
    protected void tickTock() {

    }
}
