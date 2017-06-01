package Controller;

/**
 * Created by asus on 31/05/2017.
 */

public class BarrelRolling extends Barrel {
    private final int ANIMATION_RATE = 5;
    private final int ANIMATION_RESET = 10;

    public BarrelRolling(int x, int y, int x_dir) {
        super(x,y,x_dir);
    }

    @Override
    public Barrel moveBarrel(Map map) {
        Barrel ret_val = updatePosition(map);
        this.updateSprite();
        this.tickTock();
        return ret_val;
    }

    private void updateSprite() {

    }


    private Barrel updatePosition(Map map){
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>(this.position.getFirst()+this.x_direction*this.getXSpeed() , this.position.getSecond());
        new_pos.setFirst( map.checkOutOfScreenWidth(new_pos.getFirst(), rep_size.getFirst()));
        new_pos.setSecond(map.checkOutOfScreenHeight(new_pos.getSecond(), rep_size.getSecond()));
        int new_y;
        Barrel ret_val = this;
        if ( (new_y =  map.collidesBottom(new_pos, this.rep_size.getFirst())) != -1){ //collided
            new_pos.setSecond(new_y);
            this.setPos(new_pos);
        }
        else
            ret_val = new BarrelFall(new_pos.getFirst(), new_pos.getSecond(),this.x_direction);

        return ret_val;
    }


    @Override
    protected void tickTock() {
        if ( ANIMATION_RESET == this.tick )
            this.tick = 0;

        this.tick++;

    }
}
