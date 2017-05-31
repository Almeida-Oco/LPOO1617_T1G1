package Controller;

/**
 * Created by asus on 04/05/2017.
 */

public abstract class Barrel extends Entity {
    protected final int GO_LEFT = -1;
    protected final int GO_RIGHT = 1;
    protected int tick;

    protected Barrel(int x, int y) {
        super(x,y);
        this.current_type = type.BARREL_ROLLING;
        this.tick = 0;
    }

    @Override
    public void setType(type t) {
        if (    t == type.BARREL_FALL_BACK || t == type.BARREL_FALL_FRONT || t == type.BARREL_ROLLING ||
                t == type.FIRE_BARREL_FALL_BACK || t == type.FIRE_BARREL_FALL_FRONT || t == type.FIRE_BARREL_ROLLING )
            this.current_type = t;
    }

    public static Barrel createBarrel(int x , int y){
        return new BarrelRolling(x,y);
    }

    public abstract Barrel moveBarrel( Map map, int x_move , int y_move );

    /**
     * @brief Represents the passing of time in the game, each time character moves this should be called
     */
    protected abstract void tickTock();



}
