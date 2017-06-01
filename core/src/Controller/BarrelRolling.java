package Controller;

/**
 * Created by asus on 31/05/2017.
 */

public class BarrelRolling extends Barrel {
    private final int ANIMATION_RATE = 5;
    private final int ANIMATION_RESET = 10;

    public BarrelRolling(int x, int y, int x_dir) {
        super(x,y,x_dir);
        this.current_type = type.BARREL_ROLLING;
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
        Pair<Integer,Integer> new_pos = new Pair<Integer, Integer>(this.position.getFirst()+ this.x_direction*this.getXSpeed() , this.position.getSecond());
        new_pos.setFirst( map.checkOutOfScreenWidth(new_pos.getFirst(), rep_size.getFirst()));
        new_pos.setSecond(map.checkOutOfScreenHeight(new_pos.getSecond(), rep_size.getSecond()));
        Barrel ret_val = this;
        if ( map.collidesBottom(new_pos, this.rep_size.getFirst()) != -1)
            this.setPos(new_pos);
        else
            ret_val = this.checkCraneSlope(map);

        return ret_val;
    }


    private Barrel checkCraneSlope( Map map ) {
        Pair<Integer, Integer> lower_pos = new Pair<Integer, Integer>(this.getX(), this.getY() - (int) map.getMapTileHeight());
        if (map.collidesBottom(lower_pos, this.rep_size.getFirst()) == -1)
            return new BarrelFall(lower_pos.getFirst(),lower_pos.getSecond(),this.x_direction);

        this.setPos(lower_pos);
        return this;
    }

    @Override
    protected void tickTock() {
        if ( ANIMATION_RESET == this.tick )
            this.tick = 0;

        this.tick++;

    }
}
