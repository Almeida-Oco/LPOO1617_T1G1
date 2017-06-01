package Controller;

/**
 * Created by asus on 04/05/2017.
 */

public abstract class Barrel extends Entity {
    protected int tick;
    protected int x_direction;

    protected Barrel(int x, int y, int x_dir) {
        super(x,y);
        this.tick = 0;
        this.x_direction = x_dir;
    }

    public static Barrel createBarrel(int x , int y){
        Barrel ret =  new BarrelRolling(x,y,1);
        ret.setType(type.BARREL_ROLLING);
        return ret;
    }

    @Override
    public void setType(type t) {
        if (    t == type.BARREL_FALL_BACK || t == type.BARREL_FALL_FRONT || t == type.BARREL_ROLLING ||
                t == type.FIRE_BARREL_FALL_BACK || t == type.FIRE_BARREL_FALL_FRONT || t == type.FIRE_BARREL_ROLLING )
            this.current_type = t;
    }

    public abstract Barrel moveBarrel( Map map );

    /**
     * @brief Represents the passing of time in the game, each time character moves this should be called
     */
    protected abstract void tickTock();



}
