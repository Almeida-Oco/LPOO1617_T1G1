package Controller;

/**
 * Created by asus on 04/05/2017.
 */

public class Barrel extends Entity {
    private Strategy strategy;

    @Override
    public void setType(type t) {
        if (    t == type.BARREL_FALL_BACK || t == type.BARREL_FALL_FRONT || t == type.BARREL_ROLLING ||
                t == type.FIRE_BARREL_FALL_BACK || t == type.FIRE_BARREL_FALL_FRONT || t == type.FIRE_BARREL_ROLLING )
            this.current_type = t;
    }

}
