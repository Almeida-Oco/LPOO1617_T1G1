package Controller;

/**
 * Created by asus on 31/05/2017.
 */

public class BarrelRolling extends Barrel {

    private final int ANIMATION_RATE = 5;
    private final int ANIMATION_RESET = 10;

    public BarrelRolling(int x, int y) {
        super(x,y);
    }

    @Override
    public Barrel moveBarrel(Map map, int x_move, int y_move) {
        this.updatePosition(map,x_move);
        this.updateSprite(x_move);
        return this;
    }

    private void updateSprite(int direction) {

    }


    private Barrel updatePosition( Map map , int x_move){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>(this.position.getFirst()+x_move*this.getXSpeed() , this.position.getSecond()+this.getYSpeed());
        new_pos.setFirst( map.checkOutOfScreenWidth(new_pos.getFirst(), rep_size.getFirst()));
        new_pos.setSecond(map.checkOutOfScreenHeight(new_pos.getSecond(), rep_size.getSecond()));
        int new_y;
        Barrel ret_val = this;
        if ( (new_y =  map.collidesBottom(new_pos,this.rep_size)) != -1){ //collided
            new_pos.setSecond(new_y);
            this.velocity.setSecond(0f);
            this.setPos(new_pos);
        }
        else{
            this.updateYVelocity();
            ret_val = new BarrelFall(new_pos.getFirst(), new_pos.getSecond());
        }

        return ret_val;
    }
    @Override
    protected void tickTock() {
        if ( ANIMATION_RESET == this.tick )
            this.tick = 0;

        this.tick++;

    }
}
